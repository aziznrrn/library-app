/*
 * Aplikasi sederhana manajemen buku perpustakaan
 * 
 * @Author: Muhammad Aziz Nurrohman
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;

public class Perpustakaan {
    static ArrayList<HashMap<String, Object>> books = new ArrayList<HashMap<String, Object>>();
    static ArrayList<HashMap<String, Object>> anggota = new ArrayList<HashMap<String, Object>>();
    static ArrayList<HashMap<String, Object>> peminjaman = new ArrayList<HashMap<String, Object>>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("===================================");
            System.out.println("| APLIKASI PERPUSTAKAAN           |");
            System.out.println("===================================");
            System.out.println("| 1 | Menu Buku                   |");
            System.out.println("| 2 | Menu Anggota                |");
            System.out.println("| 3 | Menu Peminjaman             |");
            System.out.println("| 0 | Keluar Aplikasi             |");
            System.out.println("===================================");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    booksMenu(scanner);
                    break;
                case 2:
                    anggotaMenu(scanner);
                    break;
                case 3:
                    peminjamanMenu(scanner);
                    break;
                case 0:
                    running = false;
                    System.out.println("Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static int generateId(ArrayList<HashMap<String, Object>> data) {
        if (data.size() == 0) {
            return 1;
        }

        int id = 0;
        for (HashMap<String, Object> item : data) {
            int currentId = (int) item.get("id");
            if (currentId > id) {
                id = currentId;
            }
        }

        return id + 1;
    }

    static void enterBeforeContinue() {
        System.out.println("Tekan enter untuk melanjutkan...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void showAvailableId(ArrayList<HashMap<String, Object>> data) {
        System.out.print("(ID yang tersedia: ");
        for (HashMap<String, Object> item : data) {
            System.out.print(item.get("id") + " ");
        }
        System.out.print(") ");
    }

    static void booksMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("===================================");
            System.out.println("| MENU BUKU                       |");
            System.out.println("===================================");
            System.out.println("| 1 | Tambah buku                 |");
            System.out.println("| 2 | Edit buku                   |");
            System.out.println("| 3 | Hapus buku                  |");
            System.out.println("| 4 | Tampilkan daftar buku       |");
            System.out.println("| 0 | Keluar Menu                 |");
            System.out.println("===================================");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    saveBook(scanner);
                    break;
                case 2:
                    editBook(scanner);
                    break;
                case 3:
                    removeBook(scanner);
                    break;
                case 4:
                    showBooks();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void saveBook(Scanner scanner) {
        int id = generateId(Perpustakaan.books);

        System.out.print("Judul: ");
        String judul = scanner.nextLine();

        System.out.print("Penulis: ");
        String penulis = scanner.nextLine();

        System.out.print("Penerbit: ");
        String penerbit = scanner.nextLine();

        System.out.print("Tahun terbit: ");
        String tahun_terbit = scanner.nextLine();

        HashMap<String, Object> new_book = new HashMap<String, Object>();
        new_book.put("id", id);
        new_book.put("judul", judul);
        new_book.put("penulis", penulis);
        new_book.put("penerbit", penerbit);
        new_book.put("tahun_terbit", tahun_terbit);

        books.add(new_book);

        System.out.println("Buku berhasil ditambahkan!");
        enterBeforeContinue();
    }

    static void editBook(Scanner scanner) {
        System.out.print("ID buku yang akan diedit: ");
        showAvailableId(Perpustakaan.books);
        int id = scanner.nextInt();
        scanner.nextLine();

        HashMap<String, Object> book = findById(Perpustakaan.books, id);
        if (book == null) {
            System.out.println("Buku dengan ID " + id + " tidak ditemukan.");
            enterBeforeContinue();
        } else {
            System.out.print("Judul: (" + book.get("judul") + ") ");
            String judul = scanner.nextLine();
    
            System.out.print("Penulis: (" + book.get("penulis") + ") ");
            String penulis = scanner.nextLine();
    
            System.out.print("Penerbit: (" + book.get("penerbit") + ") ");
            String penerbit = scanner.nextLine();
    
            System.out.print("Tahun terbit: (" + book.get("tahun_terbit") + ") ");
            String tahun_terbit = scanner.nextLine();
    
            if (!judul.equals("")) book.put("judul", judul);
            if (!penulis.equals("")) book.put("penulis", penulis);
            if (!penerbit.equals("")) book.put("penerbit", penerbit);
            if (!tahun_terbit.equals("")) book.put("tahun_terbit", tahun_terbit);
    
            System.out.println("Buku berhasil diedit!");
    
            enterBeforeContinue();
        }
    }

    static void removeBook(Scanner scanner) {
        if (Perpustakaan.books.size() == 0) {
            System.out.println("Daftar buku kosong.");
            enterBeforeContinue();
        } else {
            System.out.print("ID buku yang akan dihapus: ");
            showAvailableId(Perpustakaan.books);
            int id = scanner.nextInt();
    
            boolean removed = false;
            if (findById(Perpustakaan.books, id) != null) {
                Perpustakaan.books.remove(findById(Perpustakaan.books, id));
                removed = true;
            }
    
            if (removed) System.out.println("Buku berhasil dihapus!");
            else System.out.println("Buku dengan ID " + id + " tidak ditemukan.");
    
            enterBeforeContinue();
        }
    }

    static void showBooks() {
        if (books.size() == 0) {
            System.out.println("Daftar buku kosong.");
            enterBeforeContinue();
        } else {
            for (HashMap<String, Object> book : books) {
                System.out.println("ID: " + book.get("id"));
                System.out.println("Judul: " + book.get("judul"));
                System.out.println("Penulis: " + book.get("penulis"));
                System.out.println("Penerbit: " + book.get("penerbit"));
                System.out.println("Tahun terbit: " + book.get("tahun_terbit"));
                System.out.println();
            }
    
            enterBeforeContinue();
        }
    }

    static void anggotaMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("===================================");
            System.out.println("| MENU ANGGOTA                    |");
            System.out.println("===================================");
            System.out.println("| 1 | Tambah anggota              |");
            System.out.println("| 2 | Edit anggota                |");
            System.out.println("| 3 | Hapus anggota               |");
            System.out.println("| 4 | Tampilkan daftar anggota    |");
            System.out.println("| 0 | Keluar Menu                 |");
            System.out.println("===================================");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    saveAnggota(scanner);
                    break;
                case 2:
                    editAnggota(scanner);
                    break;
                case 3:
                    removeAnggota(scanner);
                    break;
                case 4:
                    showAnggotas();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");   
            }
        }
    }

    static void saveAnggota(Scanner scanner) {
        int id = generateId(Perpustakaan.anggota);

        System.out.print("Nama: ");
        String nama = scanner.nextLine();

        System.out.print("Alamat: ");
        String alamat = scanner.nextLine();

        System.out.print("No. HP: ");
        String no_hp = scanner.nextLine();

        HashMap<String, Object> new_anggota = new HashMap<String, Object>();
        new_anggota.put("id", id);
        new_anggota.put("nama", nama);
        new_anggota.put("alamat", alamat);
        new_anggota.put("no_hp", no_hp);

        Perpustakaan.anggota.add(new_anggota);

        System.out.println("Anggota berhasil ditambahkan!");
        enterBeforeContinue();
    }

    static HashMap<String, Object> findById(ArrayList<HashMap<String, Object>> data, int id) {
        for (HashMap<String, Object> item : data) {
            if ((int) item.get("id") == id) return item;
        }

        return null;
    }

    static void editAnggota(Scanner scanner) {
        System.out.print("ID anggota yang akan diedit: ");
        showAvailableId(Perpustakaan.anggota);
        int id = scanner.nextInt();
        scanner.nextLine();

        HashMap<String, Object> anggota = findById(Perpustakaan.anggota, id);
        if (anggota == null) {
            System.out.println("Anggota dengan ID " + id + " tidak ditemukan.");
            enterBeforeContinue();
        } else {
            System.out.print("Nama: (" + anggota.get("nama") + ") ");
            String nama = scanner.nextLine();
    
            System.out.print("Alamat: (" + anggota.get("alamat") + ") ");
            String alamat = scanner.nextLine();
    
            System.out.print("No. HP: (" + anggota.get("no_hp") + ") ");
            String no_hp = scanner.nextLine();
    
            if (nama.equals("")) nama = (String) anggota.get("nama");
            if (alamat.equals("")) alamat = (String) anggota.get("alamat");
            if (no_hp.equals("")) no_hp = (String) anggota.get("no_hp");
    
            System.out.println("Anggota berhasil diedit!");
    
            enterBeforeContinue();
        }
    }

    static void removeAnggota(Scanner scanner) {
        if (Perpustakaan.anggota.size() == 0) {
            System.out.println("Daftar anggota kosong.");
            enterBeforeContinue();
        }

        System.out.print("ID anggota yang akan dihapus: ");
        showAvailableId(Perpustakaan.anggota);
        int id = scanner.nextInt();

        boolean removed = false;
        if (findById(Perpustakaan.anggota, id) != null) {
            Perpustakaan.anggota.remove(findById(Perpustakaan.anggota, id));
            removed = true;
        }

        if (removed) System.out.println("Anggota berhasil dihapus!");
        else System.out.println("Anggota dengan ID " + id + " tidak ditemukan.");

        enterBeforeContinue();
    }

    static void showAnggotas() {
        if (Perpustakaan.anggota.size() == 0) {
            System.out.println("Daftar anggota kosong.");
            enterBeforeContinue();
        } else {
            for (HashMap<String, Object> anggota : Perpustakaan.anggota) {
                System.out.println("ID: " + anggota.get("id"));
                System.out.println("Nama: " + anggota.get("nama"));
                System.out.println("Alamat: " + anggota.get("alamat"));
                System.out.println("No. HP: " + anggota.get("no_hp"));
                System.out.println();
            }
    
            enterBeforeContinue();
        }
    }

    static void peminjamanMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            clearScreen();
            System.out.println("===================================");
            System.out.println("| MENU PEMINJAMAN                 |");
            System.out.println("===================================");
            System.out.println("| 1 | Tambah peminjaman           |");
            System.out.println("| 2 | Edit peminjaman             |");
            System.out.println("| 3 | Hapus peminjaman            |");
            System.out.println("| 4 | Tampilkan daftar peminjaman |");
            System.out.println("| 0 | Keluar Menu                 |");
            System.out.println("===================================");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    savePeminjaman(scanner);
                    break;
                case 2:
                    editPeminjaman(scanner);
                    break;
                case 3:
                    removePeminjaman(scanner);
                    break;
                case 4:
                    showPeminjaman();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void savePeminjaman(Scanner scanner) {
        int id = generateId(Perpustakaan.peminjaman);

        System.out.print("ID anggota: ");
        showAvailableId(Perpustakaan.anggota);
        int id_anggota = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID buku: ");
        showAvailableId(Perpustakaan.books);
        int id_book = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tanggal peminjaman: ");
        String tanggal_peminjaman = scanner.nextLine();

        System.out.print("Tanggal pengembalian: (kosongkan jika belum dikembalikan) ");
        String tanggal_pengembalian = scanner.nextLine();

        System.out.print("Denda: (kosongkan jika tidak ada denda) ");
        String denda = scanner.nextLine();

        HashMap<String, Object> new_peminjaman = new HashMap<String, Object>();
        new_peminjaman.put("id", id);
        new_peminjaman.put("id_anggota", id_anggota);
        new_peminjaman.put("id_book", id_book);
        new_peminjaman.put("tanggal_peminjaman", tanggal_peminjaman);
        new_peminjaman.put("tanggal_pengembalian", tanggal_pengembalian);
        new_peminjaman.put("denda", denda);

        Perpustakaan.peminjaman.add(new_peminjaman);

        System.out.println("Peminjaman berhasil ditambahkan!");
        enterBeforeContinue();
    }

    static void editPeminjaman(Scanner scanner) {
        System.out.print("ID peminjaman yang akan diedit: ");
        showAvailableId(Perpustakaan.peminjaman);
        int id = scanner.nextInt();
        scanner.nextLine();

        HashMap<String, Object> peminjaman = findById(Perpustakaan.peminjaman, id);
        if (peminjaman == null) {
            System.out.println("Peminjaman dengan ID " + id + " tidak ditemukan.");
            enterBeforeContinue();
        } else {
            System.out.print("Tanggal Pengembalian: (" + peminjaman.get("tanggal_pengembalian") + ") ");
            String tanggal_pengembalian = scanner.nextLine();
    
            System.out.print("Denda: (" + peminjaman.get("denda") + ") ");
            String denda = scanner.nextLine();
    
            if (!tanggal_pengembalian.equals("")) peminjaman.put("tanggal_pengembalian", tanggal_pengembalian);
            if (!denda.equals("")) peminjaman.put("denda", denda);
    
            System.out.println("Peminjaman berhasil diedit!");
    
            enterBeforeContinue();
        }
    }

    static void removePeminjaman(Scanner scanner) {
        if (Perpustakaan.peminjaman.size() == 0) {
            System.out.println("Daftar peminjaman kosong.");
            enterBeforeContinue();
        } else {
            System.out.print("ID peminjaman yang akan dihapus: ");
            showAvailableId(Perpustakaan.peminjaman);
            int id = scanner.nextInt();
    
            boolean removed = false;
            if (findById(Perpustakaan.peminjaman, id) != null) {
                Perpustakaan.peminjaman.remove(findById(Perpustakaan.peminjaman, id));
                removed = true;
            }
    
            if (removed) System.out.println("Peminjaman berhasil dihapus!");
            else System.out.println("Peminjaman dengan ID " + id + " tidak ditemukan.");
    
            enterBeforeContinue();
        }
    }

    static void showPeminjaman() {
        if (Perpustakaan.peminjaman.size() == 0) {
            System.out.println("Daftar peminjaman kosong.");
            enterBeforeContinue();
        } else {
            for (HashMap<String, Object> peminjaman : Perpustakaan.peminjaman) {
                String nama = "Anggota tidak ditemukan";
                if (findById(Perpustakaan.anggota, (int) peminjaman.get("id_anggota")) != null) {
                    nama = (String) findById(Perpustakaan.anggota, (int) peminjaman.get("id_anggota")).get("nama");
                }

                String judul = "Buku tidak ditemukan";
                if (findById(Perpustakaan.books, (int) peminjaman.get("id_book")) != null) {
                    judul = (String) findById(Perpustakaan.books, (int) peminjaman.get("id_book")).get("judul");
                }

                System.out.println("ID: " + peminjaman.get("id"));
                System.out.println("Anggota: " + nama);
                System.out.println("Buku: " + judul);
                System.out.println("Tanggal peminjaman: " + peminjaman.get("tanggal_peminjaman"));
                System.out.println("Tanggal pengembalian: " + peminjaman.get("tanggal_pengembalian"));
                System.out.println("Denda: " + peminjaman.get("denda"));
                System.out.println();
            }

            enterBeforeContinue();
        }
    }
}