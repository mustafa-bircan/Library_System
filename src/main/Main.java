package main;

import main.java.model.book.Book;

import main.java.model.book.enums.DefaultBooks;
import main.java.model.library.Library;
import main.java.model.person.Reader;
import main.java.model.person.enums.ReaderLimit;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Library library = new Library();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Kütüphanede bulunan kitaplar:");
        System.out.println("------------------------");
        for (DefaultBooks defaultBooks : DefaultBooks.values()) {
            library.newBook(defaultBooks.createBook());
            System.out.println("- " + defaultBooks.createBook().getTitle());
        }
        System.out.println("------------------------");

        while (true) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1 -> showAllBooks();
                case 2 -> showAvailableBooks();
                case 3 -> addNewReader();
                case 4 -> lendBook();
                case 5 -> returnBook();
                case 6 -> showAllReaders();
                case 0 -> {
                    System.out.println("Kütüphane Yönetim Sistemini Kullandığınız İçin Teşekkür Ederiz!");
                    return;
                }
                default -> System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Kütüphane Yönetim Sistemi ===");
        System.out.println("1. Tüm Kitapları Göster");
        System.out.println("2. Boşta Olan Kitapları Göster");
        System.out.println("3. Yeni Okuyucu Ekle");
        System.out.println("4. Ödünç Kitap Ver");
        System.out.println("5. Kitap İadesi Al");
        System.out.println("6. Tüm Okuyucuları Göster");
        System.out.println("0. Çıkış");
        System.out.print("\nSeçim İçin Tuşlama Yapın: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void showAllBooks() {
        System.out.println("\n=== Kütüphanede Bulunan Tüm Kitaplar ===");
        List<Book> books = library.getBooks();
        if (books.isEmpty()) {
            System.out.println("Kütüphanede kitap bulunmamaktadır.");
            return;
        }
        library.showBook();
    }

    private static void showAvailableBooks() {
        System.out.println("\n=== Mevcut Kitaplar ===");
        List<Book> availableBooks = library.getAvailableBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("Ödünç verilebilecek kitap yok.");
            return;
        }
        availableBooks.forEach(book -> {
            System.out.println("\n------------------------");
            book.display();
        });
        System.out.println("------------------------");
    }

    private static void addNewReader() {
        System.out.println("\n=== Yeni Okuyucu Ekle ===");
        System.out.print("Okuyucu adını girin: ");
        String name = scanner.nextLine();

        System.out.println("\nOkuyucu Tipini Seçin:");
        System.out.println("1. STANDART (Maksimum 5 kitap, 14 gün, 0,50 $/gün ceza)");
        System.out.println("2. VIP (Maksimum 10 kitap, 21 gün, günlük 0,25 $ ceza)");
        System.out.println("3. SINIRLI (En fazla 2 kitap, 7 gün, günlük 1,00$ ceza)");
        System.out.print("Seçimi (1-3) girin: ");

        ReaderLimit readerLimit;
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            readerLimit = switch (choice) {
                case 1 -> ReaderLimit.STANDART;
                case 2 -> ReaderLimit.VIP;
                case 3 -> ReaderLimit.RESTRICTED;
                default -> throw new IllegalArgumentException("Geçersiz okuyucu türü seçildi");
            };
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz giriş! Lütfen bir sayı girin.");
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Okuyucu adı boş olamaz!");
            return;
        }

        try {
            Reader reader = new Reader(name, readerLimit);
            library.addReader(reader);
            System.out.println("Okuyucu başarıyla şu şekilde eklendi " + readerLimit.name() + "!");
            System.out.println("Maksimum kitap: " + readerLimit.getMaxBooks());
            System.out.println("Maksiumum gün: " + readerLimit.getMaxDays());
            System.out.println("Gün başına ceza: ₺" + String.format("%.2f", readerLimit.getFinePerDay()));
        } catch (IllegalStateException e) {
            System.out.println("Hata " + e.getMessage());
        }
    }

    private static void lendBook() {
        System.out.println("\n=== Ödünç Kitap ===");

        List<Book> availableBooks = library.getAvailableBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("Ödünç verilebilecek kitap yok.");
            return;
        }

        System.out.println("Mevcut kitaplar:");
        availableBooks.forEach(book ->
                System.out.println(book.getBookID() + " - " + book.getTitle()));

        System.out.print("\nKitap ID'si girin: ");
        String bookId = scanner.nextLine();

        System.out.print("Okuyucu adını girin: ");
        String readerName = scanner.nextLine();

        try {
            library.lendBook(bookId, readerName);
            System.out.println("Kitap başarıyla ödünç verildi!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    private static void returnBook() {
        System.out.println("\n=== İade Kitap ===");
        System.out.print("Kitap ID'si girin: ");
        String bookId = scanner.nextLine();

        System.out.print("Okuyucu adını girin: ");
        String readerName = scanner.nextLine();

        try {
            library.takeBackBook(bookId, readerName);
            System.out.println("Kitap başarıyla iade edildi!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    private static void showAllReaders() {
        System.out.println("\n=== Tüm Okuyucular ===");
        List<Reader> readers = library.getReaders();

        if (readers.isEmpty()) {
            System.out.println("Kütüphanede kayıtlı okuyucu yok.");
            return;
        }

        readers.forEach(reader -> {
            System.out.println("\n------------------------");
            System.out.println("Ad: " + reader.getName());
            List<Book> borrowedBooks = reader.getBorrowedBooks();
            if (!borrowedBooks.isEmpty()) {
                System.out.println("Ödünç alınmış kitaplar:");
                borrowedBooks.forEach(book ->
                        System.out.println("- " + book.getTitle()));
            } else {
                System.out.println("Ödünç alınan kitap yok");
            }
        });
        System.out.println("------------------------");
    }
}