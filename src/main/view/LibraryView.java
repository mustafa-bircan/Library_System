package main.view;

import main.controller.LibraryController;
import main.model.book.Book;
import main.model.book.enums.BookStatus;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class LibraryView {
    private final LibraryController controller;
    private final Scanner scanner;

    public LibraryView(LibraryController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("\n=== KÜTÜPHANE YÖNETİM SİSTEMİ ===");
        System.out.println("=== KİTAP İŞLEMLERİ ===");
        System.out.println("1. Tüm Kitapları Göster");
        System.out.println("2. Boşta Olan Kitapları Göster");
        System.out.println("3. Yeni Kitap Ekle");
        System.out.println("4. Kitap Sil");
        System.out.println("5. Kitap Bilgilerini Güncelle");
        System.out.println("6. Kitap Durumunu Güncelle");
        System.out.println("7. Dergi Ekle");
        System.out.println("8. Ders Kitabı Ekle");

        System.out.println("\n=== ÜYE İŞLEMLERİ ===");
        System.out.println("9. Yeni Okuyucu Ekle");
        System.out.println("10. Okuyucu Sil");
        System.out.println("11. Okuyucu Bilgilerini Güncelle");
        System.out.println("12. Tüm Okuyucuları Göster");
        System.out.println("13. Okuyucu Geçmişini Görüntüle");

        System.out.println("\n=== ÖDÜNÇ İŞLEMLERİ ===");
        System.out.println("14. Ödünç Kitap Ver");
        System.out.println("15. Kitap İadesi Al");
        System.out.println("16. Ceza Hesapla ve Tahsil Et");

        System.out.println("\n=== RAPORLAMA ===");
        System.out.println("17. En Çok Okunan Kitaplar");
        System.out.println("18. En Aktif Okuyucular");
        System.out.println("19. Gecikmiş Kitaplar");
        System.out.println("20. Kitap İstatistikleri");

        System.out.println("\n0. ÇIKIŞ");
        System.out.print("\nSeçim İçin Tuşlama Yapın: ");
    }

    public int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void showAllBooks() {
        System.out.println("\n=== Kütüphanede Bulunan Tüm Kitaplar ===");
        Map<String, Book> books = controller.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("Kütüphanede kitap bulunmamaktadır.");
            return;
        }

        books.forEach((id, book) -> {
            System.out.println("\n------------------------");
            System.out.println("ID: " + id);
            System.out.println("Başlık: " + book.getTitle());
            System.out.println("Yazar: " + book.getAuthor());
            System.out.println("Durum: " + book.getStatus().getDisplayName());
        });
        System.out.println("------------------------");
    }

    public void showAvailableBooks() {
        System.out.println("\n=== Mevcut Kitaplar ===");
        Set<Book> availableBooks = controller.getAvailableBooks();

        if (availableBooks.isEmpty()) {
            System.out.println("Ödünç verilebilecek kitap yok.");
        }
        availableBooks.forEach(book -> {
            System.out.println("\n------------------------");
            System.out.println("ID: " + book.getBookID());
            System.out.println("Başlık: " + book.getTitle());
            System.out.println("Yazar: " + book.getAuthor());
        });
        System.out.println("------------------------");
    }

    public void handleNewBook() {
        System.out.println("\n=== Yeni Kitap Ekle ===");

        System.out.println("Kitap adı girin: ");
        String title = scanner.nextLine();

        System.out.println("Yazar adı girin: ");
        String author = scanner.nextLine();

        System.out.println("Fiyat girin: ");
        double price = 0.0;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz fiyat formatı (Örn: 25.50)");
        }

        System.out.println("Baskı bilgisi girin: ");
        String edition = scanner.nextLine();

        try {
            controller.addNewBook(title,author,price,edition);
            System.out.println("\nKitap başarıyla eklendi");
        } catch (IllegalArgumentException e) {
            System.out.println("Hata: " + e.getMessage());
        }

    }

    public void handleDeleteBook() {
        System.out.println("\n=== Kitap Sil ===");
        Map<String, Book> books = controller.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("Kütüphanede silinecek kitap bulunmamaktadır.");
            return;
        }

        System.out.println("\nMevcut kitaplar:");
        books.forEach((id, book) -> {
            System.out.println("------------------------");
            System.out.println("ID: " + id);
            System.out.println("Başlık: " + book.getTitle());
            System.out.println("Yazar: " + book.getAuthor());
            System.out.println("Durum: " + book.getStatus().getDisplayName());
        });

        System.out.print("\nSilmek istediğiniz kitabın ID'sini girin: ");
        String bookId = scanner.nextLine();

        System.out.print("Bu kitabı silmek istediğinizden emin misiniz? (E/H): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("E")) {
            try {
                controller.deleteBook(bookId);
                System.out.println("Kitap başarıyla silindi!");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Hata: " + e.getMessage());
            }
        } else {
            System.out.println("Kitap silme işlemi iptal edildi.");
        }
    }

    public void handleUpdateBook() {
        System.out.println("\n=== Kitap Bilgilerini Güncelle ===");
        Map<String, Book> books = controller.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("Kütüphanede güncellenecek kitap bulunmamaktadır.");
            return;
        }

        System.out.println("\nMevcut kitaplar:");
        books.forEach((id, book) -> {
            System.out.println("------------------------");
            System.out.println("ID: " + id);
            System.out.println("Başlık: " + book.getTitle());
            System.out.println("Yazar: " + book.getAuthor());
            System.out.println("Durum: " + book.getStatus().getDisplayName());
        });

        System.out.print("\nGüncellemek istediğiniz kitabın ID'sini girin: ");
        String bookId = scanner.nextLine();

        try {
            Book existingBook = controller.getAllBooks().get(bookId);
            if (existingBook == null) {
                System.out.println("Hata: Belirtilen ID'ye sahip kitap bulunamadı!");
                return;
            }

            System.out.println("\nMevcut bilgiler:");
            System.out.println("Başlık: " + existingBook.getTitle());
            System.out.println("Yazar: " + existingBook.getAuthor());

            System.out.print("\nYeni başlığı girin (değiştirmemek için boş bırakın): ");
            String newTitle = scanner.nextLine();
            newTitle = newTitle.trim().isEmpty() ? existingBook.getTitle() : newTitle;

            System.out.print("Yeni yazarı girin (değiştirmemek için boş bırakın): ");
            String newAuthor = scanner.nextLine();
            newAuthor = newAuthor.trim().isEmpty() ? existingBook.getAuthor() : newAuthor;

            System.out.print("Yeni fiyatı girin (değiştirmemek için -1 girin): ");
            double newPrice = Double.parseDouble(scanner.nextLine());
            newPrice = newPrice < 0 ? existingBook.getPrice() : newPrice;

            System.out.print("Yeni baskı bilgisini girin (değiştirmemek için boş bırakın): ");
            String newEdition = scanner.nextLine();
            newEdition = newEdition.trim().isEmpty() ? existingBook.getEdition() : newEdition;

            controller.updateBook(bookId, newTitle, newAuthor, newPrice, newEdition);
            System.out.println("\nKitap bilgileri başarıyla güncellendi!");

        } catch (NumberFormatException e) {
            System.out.println("Hata: Geçersiz fiyat formatı!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public void handleUpdateBookStatus() {
        System.out.println("\n=== Kitap Durumunu Güncelle ===");
        Map<String, Book> books = controller.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("Kütüphanede kitap bulunmamaktadır.");
            return;
        }

        System.out.println("\nMevcut kitaplar:");
        books.forEach((id, book) -> {
            System.out.println("------------------------");
            System.out.println("ID: " + id);
            System.out.println("Başlık: " + book.getTitle());
            System.out.println("Yazar: " + book.getAuthor());
            System.out.println("Mevcut Durum: " + book.getStatus().getDisplayName());
        });

        System.out.print("\nDurumu güncellenecek kitabın ID'sini girin: ");
        String bookId = scanner.nextLine();

        System.out.println("\nYeni durumu seçin:");
        System.out.println("1. MÜSAİT");
        System.out.println("2. BAKIMDA");
        System.out.println("3. KAYIP");
        System.out.print("Seçiminiz (1-3): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            BookStatus newStatus = switch (choice) {
                case 1 -> BookStatus.AVAILABLE;
                case 2 -> BookStatus.MAINTENANCE;
                case 3 -> BookStatus.LOST;
                default -> throw new IllegalArgumentException("Geçersiz durum seçimi!");
            };

            controller.updateBookStatus(bookId, newStatus);
            System.out.println("Kitap durumu başarıyla güncellendi!");
        } catch (NumberFormatException e) {
            System.out.println("Hata: Geçersiz seçim!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
}
