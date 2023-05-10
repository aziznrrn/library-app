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
    private static ArrayList<HashMap<String, Object>> books = new ArrayList<HashMap<String, Object>>();
    private static ArrayList<HashMap<String, Object>> members = new ArrayList<HashMap<String, Object>>();
    private static ArrayList<HashMap<String, Object>> borrowing_records = new ArrayList<HashMap<String, Object>>();

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
                    membersMenu(scanner);
                    break;
                case 3:
                    borrowingRecordsMenu(scanner);
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

    private static void clearScreen() {
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

    private static int generateId(ArrayList<HashMap<String, Object>> data) {
        int id = 0;
        for (HashMap<String, Object> item : data) {
            int currentId = (int) item.get("id");
            if (currentId > id) {
                id = currentId;
            }
        }

        return id + 1;
    }

    private static void enterBeforeContinue() {
        System.out.println("Tekan enter untuk melanjutkan...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showAvailableId(ArrayList<HashMap<String, Object>> data) {
        System.out.print("( ID yang tersedia: ");
        for (HashMap<String, Object> item : data) {
            System.out.print(item.get("id") + " ");
        }
        System.out.print(") ");
    }

    private static HashMap<String, Object> findById(ArrayList<HashMap<String, Object>> data, int id) {
        for (HashMap<String, Object> item : data) {
            if ((int) item.get("id") == id) return item;
        }

        return null;
    }

    private static void booksMenu(Scanner scanner) {
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
                    addBook(scanner);
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

    private static void addBook(Scanner scanner) {
        int id = generateId(books);

        System.out.print("Judul: ");
        String judul = scanner.nextLine();

        System.out.print("Penulis: ");
        String penulis = scanner.nextLine();

        System.out.print("Penerbit: ");
        String penerbit = scanner.nextLine();

        System.out.print("Tahun terbit: ");
        String tahun_terbit = scanner.nextLine();

        HashMap<String, Object> book = new HashMap<String, Object>();
        book.put("id", id);
        book.put("judul", judul);
        book.put("penulis", penulis);
        book.put("penerbit", penerbit);
        book.put("tahun_terbit", tahun_terbit);

        books.add(book);

        System.out.println();
        System.out.println("Buku berhasil ditambahkan!");
        enterBeforeContinue();
    }

    private static void editBook(Scanner scanner) {
        if (books.size() == 0) {
            System.out.println("Belum ada buku yang tersimpan.");
            enterBeforeContinue();
        } else {
            System.out.print("ID buku yang akan diedit: ");
            showAvailableId(books);
            int id = scanner.nextInt();
            scanner.nextLine();

            HashMap<String, Object> book = findById(books, id);
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

                System.out.println();
                System.out.println("Buku berhasil diedit!");

                enterBeforeContinue();
            }
        }
    }

    private static void removeBook(Scanner scanner) {
        if (books.size() == 0) {
            System.out.println("Daftar buku kosong.");
            enterBeforeContinue();
        } else {
            System.out.print("ID buku yang akan dihapus: ");
            showAvailableId(books);
            int id = scanner.nextInt();

            boolean removed = false;
            if (findById(books, id) != null) {
                books.remove(findById(books, id));
                removed = true;
            }

            System.out.println();
            if (removed) System.out.println("Buku berhasil dihapus!");
            else System.out.println("Buku dengan ID " + id + " tidak ditemukan.");

            enterBeforeContinue();
        }
    }

    private static void showBooks() {
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

    private static void membersMenu(Scanner scanner) {
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
                    addMember(scanner);
                    break;
                case 2:
                    editMember(scanner);
                    break;
                case 3:
                    removeMember(scanner);
                    break;
                case 4:
                    showMembers();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void addMember(Scanner scanner) {
        int id = generateId(members);

        System.out.print("Nama: ");
        String nama = scanner.nextLine();

        System.out.print("Alamat: ");
        String alamat = scanner.nextLine();

        System.out.print("No. HP: ");
        String no_hp = scanner.nextLine();

        HashMap<String, Object> member = new HashMap<String, Object>();
        member.put("id", id);
        member.put("nama", nama);
        member.put("alamat", alamat);
        member.put("no_hp", no_hp);

        members.add(member);

        System.out.println();
        System.out.println("Anggota berhasil ditambahkan!");
        enterBeforeContinue();
    }

    private static void editMember(Scanner scanner) {
        if (members.size() == 0) {
            System.out.println("Daftar anggota kosong.");
            enterBeforeContinue();
        } else {
            System.out.print("ID anggota yang akan diedit: ");
            showAvailableId(members);
            int id = scanner.nextInt();
            scanner.nextLine();

            HashMap<String, Object> member = findById(members, id);
            if (member == null) {
                System.out.println("Anggota dengan ID " + id + " tidak ditemukan.");
                enterBeforeContinue();
            } else {
                System.out.print("Nama: (" + member.get("nama") + ") ");
                String nama = scanner.nextLine();

                System.out.print("Alamat: (" + member.get("alamat") + ") ");
                String alamat = scanner.nextLine();

                System.out.print("No. HP: (" + member.get("no_hp") + ") ");
                String no_hp = scanner.nextLine();

                if (!nama.equals("")) member.put("nama", nama);
                if (!alamat.equals("")) member.put("alamat", alamat);
                if (!no_hp.equals("")) member.put("no_hp", no_hp);

                System.out.println();
                System.out.println("Anggota berhasil diedit!");

                enterBeforeContinue();
            }
        }
    }

    private static void removeMember(Scanner scanner) {
        if (members.size() == 0) {
            System.out.println("Daftar anggota kosong.");
            enterBeforeContinue();
        }

        System.out.print("ID anggota yang akan dihapus: ");
        showAvailableId(members);
        int id = scanner.nextInt();

        boolean removed = false;
        if (findById(members, id) != null) {
            members.remove(findById(members, id));
            removed = true;
        }

        System.out.println();
        if (removed) System.out.println("Anggota berhasil dihapus!");
        else System.out.println("Anggota dengan ID " + id + " tidak ditemukan.");

        enterBeforeContinue();
    }

    private static void showMembers() {
        if (members.size() == 0) {
            System.out.println("Daftar anggota kosong.");
            enterBeforeContinue();
        } else {
            for (HashMap<String, Object> member : members) {
                System.out.println("ID: " + member.get("id"));
                System.out.println("Nama: " + member.get("nama"));
                System.out.println("Alamat: " + member.get("alamat"));
                System.out.println("No. HP: " + member.get("no_hp"));
                System.out.println();
            }

            enterBeforeContinue();
        }
    }

    private static void borrowingRecordsMenu(Scanner scanner) {
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
                    addBorrowingRecord(scanner);
                    break;
                case 2:
                    editBorrowingRecord(scanner);
                    break;
                case 3:
                    removeBorrowingRecord(scanner);
                    break;
                case 4:
                    showBorrowingRecords();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void addBorrowingRecord(Scanner scanner) {
        int id = generateId(borrowing_records);

        System.out.print("ID anggota: ");
        showAvailableId(members);
        int id_member = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID buku: ");
        showAvailableId(books);
        int id_book = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tanggal peminjaman: ");
        String tanggal_peminjaman = scanner.nextLine();

        System.out.print("Tanggal pengembalian: ");
        String tanggal_pengembalian = scanner.nextLine();

        System.out.print("Tanggal dikembalikan: (kosongkan jika belum dikembalikan)");
        String tanggal_dikembalikan = scanner.nextLine();

        System.out.print("Denda: (kosongkan jika tidak ada denda) ");
        String denda = scanner.nextLine();

        HashMap<String, Object> borrowing_record = new HashMap<String, Object>();
        borrowing_record.put("id", id);
        borrowing_record.put("id_member", id_member);
        borrowing_record.put("id_book", id_book);
        borrowing_record.put("tanggal_peminjaman", tanggal_peminjaman);
        borrowing_record.put("tanggal_pengembalian", tanggal_pengembalian);
        borrowing_record.put("tanggal_dikembalikan", tanggal_dikembalikan);
        borrowing_record.put("denda", denda);

        borrowing_records.add(borrowing_record);

        System.out.println();
        System.out.println("Peminjaman berhasil ditambahkan!");
        enterBeforeContinue();
    }

    private static void editBorrowingRecord(Scanner scanner) {
        if (borrowing_records.size() == 0) {
            System.out.println("Daftar peminjaman kosong.");
            enterBeforeContinue();
        } else {
            System.out.print("ID peminjaman yang akan diedit: ");
            showAvailableId(borrowing_records);
            int id = scanner.nextInt();
            scanner.nextLine();

            HashMap<String, Object> borrowing_record = findById(borrowing_records, id);
            if (borrowing_record == null) {
                System.out.println("Peminjaman dengan ID " + id + " tidak ditemukan.");
                enterBeforeContinue();
            } else {
                System.out.print("Tanggal Dikembalikan: (" + borrowing_record.get("tanggal_dikembalikan") + ") ");
                String tanggal_dikembalikan = scanner.nextLine();

                System.out.print("Denda: (" + borrowing_record.get("denda") + ") ");
                String denda = scanner.nextLine();

                if (!tanggal_dikembalikan.equals("")) borrowing_record.put("tanggal_dikembalikan", tanggal_dikembalikan);
                if (!denda.equals("")) borrowing_record.put("denda", denda);

                System.out.println();
                System.out.println("Peminjaman berhasil diedit!");

                enterBeforeContinue();
            }
        }
    }

    private static void removeBorrowingRecord(Scanner scanner) {
        if (borrowing_records.size() == 0) {
            System.out.println("Daftar peminjaman kosong.");
            enterBeforeContinue();
        } else {
            System.out.print("ID peminjaman yang akan dihapus: ");
            showAvailableId(borrowing_records);
            int id = scanner.nextInt();

            boolean removed = false;
            if (findById(borrowing_records, id) != null) {
                borrowing_records.remove(findById(borrowing_records, id));
                removed = true;
            }

            System.out.println();
            if (removed) System.out.println("Peminjaman berhasil dihapus!");
            else System.out.println("Peminjaman dengan ID " + id + " tidak ditemukan.");

            enterBeforeContinue();
        }
    }

    private static void showBorrowingRecords() {
        if (borrowing_records.size() == 0) {
            System.out.println("Daftar peminjaman kosong.");
            enterBeforeContinue();
        } else {
            for (HashMap<String, Object> borrowing_record : borrowing_records) {
                String nama = "Anggota tidak ditemukan";
                if (findById(members, (int) borrowing_record.get("id_member")) != null) {
                    nama = (String) findById(members, (int) borrowing_record.get("id_member")).get("nama");
                }

                String judul = "Buku tidak ditemukan";
                if (findById(books, (int) borrowing_record.get("id_book")) != null) {
                    judul = (String) findById(books, (int) borrowing_record.get("id_book")).get("judul");
                }

                System.out.println("ID: " + borrowing_record.get("id"));
                System.out.println("Anggota: " + nama);
                System.out.println("Buku: " + judul);
                System.out.println("Tanggal peminjaman: " + borrowing_record.get("tanggal_peminjaman"));
                System.out.println("Tanggal pengembalian: " + borrowing_record.get("tanggal_pengembalian"));
                System.out.println("Tanggal dikembalikan: " + borrowing_record.get("tanggal_dikembalikan"));
                System.out.println("Denda: " + borrowing_record.get("denda"));
                System.out.println();
            }

            enterBeforeContinue();
        }
    }
}
