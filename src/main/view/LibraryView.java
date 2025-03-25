package main.view;

import main.controller.LibraryController;
import main.model.book.Book;
import main.model.book.enums.BookStatus;
import main.model.person.Reader;
import main.model.person.enums.ReaderLimit;

import java.util.List;
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

    public void handleNewJournal() {
        System.out.println("\n=== Yeni Dergi Ekle ===");

        System.out.println("Dergi adı girin: ");
        String title = scanner.nextLine();

        System.out.println("Yazar/Editör adı girin: ");
        String author = scanner.nextLine();

        System.out.println("Fiyat girin: ");
        double price = 0.0;
        try {
            price = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz fiyat formatı (Örn: 25.50)");
            return;
        }

        System.out.println("Baskı bilgisi girin: ");
        String edition = scanner.nextLine();

        System.out.println("Dergi konusu girin: ");
        String subject = scanner.nextLine();

        System.out.println("ISSN numarası girin (XXXX-XXXX formatında): ");
        String issn = scanner.nextLine();

        System.out.println("Cilt numarası girin: ");
        int volume;
        try {
            volume = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz cilt numarası!");
            return;
        }

        System.out.println("Sayı numarası girin: ");
        int issue;
        try {
            issue = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz sayı numarası!");
            return;
        }

        try {
            controller.addNewJournal(title, author, price, edition, subject, issn, volume, issue);
            System.out.println("\nDergi başarıyla eklendi");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public void handleNewStudyBook() {
        System.out.println("\n=== Yeni Ders Kitabı Ekle ===");

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
            return;
        }

        System.out.println("Baskı bilgisi girin: ");
        String edition = scanner.nextLine();

        System.out.println("Ders/Konu adı girin: ");
        String subject = scanner.nextLine();

        System.out.println("ISBN numarası girin (XXX-XXXXXXXXXX formatında): ");
        String isbn = scanner.nextLine();

        System.out.println("Sınıf seviyesi girin (1-12): ");
        int grade;
        try {
            grade = Integer.parseInt(scanner.nextLine());
            if (grade < 1 || grade > 12) {
                System.out.println("Sınıf seviyesi 1-12 arasında olmalıdır!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz sınıf seviyesi!");
            return;
        }

        System.out.println("Yayınevi adı girin: ");
        String publisher = scanner.nextLine();

        try {
            controller.addNewStudyBook(title, author, price, edition, subject, isbn, grade, publisher);
            System.out.println("\nDers kitabı başarıyla eklendi");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public void handleNewReader() {
        System.out.println("\n=== Yeni Okuyucu Ekle ===");

        System.out.println("Okuyucu adı girin: ");
        String name = scanner.nextLine();

        System.out.println("Adres girin: ");
        String address = scanner.nextLine();

        System.out.println("Telefon numarası girin (XXX-XXXX formatında): ");
        String phoneNo = scanner.nextLine();

        System.out.println("\nOkuyucu tipini seçin:");
        System.out.println("1. STANDART (3 kitap limiti)");
        System.out.println("2. PREMIUM (5 kitap limiti)");
        System.out.println("3. VIP (10 kitap limiti)");
        System.out.print("Seçiminiz (1-3): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            ReaderLimit readerLimit = switch (choice) {
                case 1 -> ReaderLimit.STANDART;
                case 2 -> ReaderLimit.RESTRICTED;
                case 3 -> ReaderLimit.VIP;
                default -> throw new IllegalArgumentException("Geçersiz okuyucu tipi seçimi!");
            };

            controller.addNewReader(name, address, phoneNo, readerLimit);
            System.out.println("Okuyucu başarıyla eklendi!");
        } catch (NumberFormatException e) {
            System.out.println("Hata: Geçersiz seçim!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public void handleShowAllReaders() {
        System.out.println("\n=== Tüm Okuyucular ===");
        Map<String, Reader> readers = controller.getAllReaders();

        if (readers.isEmpty()) {
            System.out.println("Kayıtlı okuyucu bulunmamaktadır.");
            return;
        }

        readers.forEach((id, reader) -> {
            System.out.println("\n------------------------");
            System.out.println("ID: " + id);
            System.out.println("Ad: " + reader.getName());
            System.out.println("Üyelik Tipi: " + reader.getReaderLimit());
            System.out.println("Mevcut Kitap Sayısı: " + reader.getBorrowedBooks().size());
            System.out.println("Kalan Kitap Hakkı: " + reader.getRemainingBookLimit());
        });
        System.out.println("------------------------");
    }

    public void handleDeleteReader() {
        System.out.println("\n=== Okuyucu Sil ===");
        Map<String, Reader> readers = controller.getAllReaders();

        if (readers.isEmpty()) {
            System.out.println("Silinecek okuyucu bulunmamaktadır.");
            return;
        }

        handleShowAllReaders();

        System.out.print("\nSilmek istediğiniz okuyucunun ID'sini girin: ");
        String readerId = scanner.nextLine();

        System.out.print("Bu okuyucuyu silmek istediğinizden emin misiniz? (E/H): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("E")) {
            try {
                controller.deleteReader(readerId);
                System.out.println("Okuyucu başarıyla silindi!");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Hata: " + e.getMessage());
            }
        } else {
            System.out.println("Okuyucu silme işlemi iptal edildi.");
        }
    }

    public void handleUpdateReader() {
        System.out.println("\n=== Okuyucu Bilgilerini Güncelle ===");
        Map<String, Reader> readers = controller.getAllReaders();

        if (readers.isEmpty()) {
            System.out.println("Güncellenecek okuyucu bulunmamaktadır.");
            return;
        }

        handleShowAllReaders();

        System.out.print("\nGüncellemek istediğiniz okuyucunun ID'sini girin: ");
        String readerId = scanner.nextLine();

        try {
            Reader existingReader = controller.getAllReaders().get(readerId);
            if (existingReader == null) {
                System.out.println("Hata: Belirtilen ID'ye sahip okuyucu bulunamadı!");
                return;
            }

            System.out.println("\nMevcut bilgiler:");
            System.out.println("Ad: " + existingReader.getName());

            System.out.print("\nYeni adı girin (değiştirmemek için boş bırakın): ");
            String newName = scanner.nextLine();
            newName = newName.trim().isEmpty() ? existingReader.getName() : newName;

            System.out.print("Yeni adres girin: ");
            String newAddress = scanner.nextLine();

            System.out.print("Yeni telefon numarası girin (XXX-XXXX): ");
            String newPhoneNo = scanner.nextLine();

            System.out.println("\nYeni okuyucu tipini seçin:");
            System.out.println("1. STANDART (3 kitap)");
            System.out.println("2. PREMIUM (5 kitap)");
            System.out.println("3. VIP (10 kitap)");
            System.out.print("Seçiminiz (1-3): ");

            int choice = Integer.parseInt(scanner.nextLine());
            ReaderLimit newReaderLimit = switch (choice) {
                case 1 -> ReaderLimit.STANDART;
                case 2 -> ReaderLimit.RESTRICTED;
                case 3 -> ReaderLimit.VIP;
                default -> throw new IllegalArgumentException("Geçersiz okuyucu tipi!");
            };

            controller.updateReader(readerId, newName, newAddress, newPhoneNo, newReaderLimit);
            System.out.println("\nOkuyucu bilgileri başarıyla güncellendi!");

        } catch (NumberFormatException e) {
            System.out.println("Hata: Geçersiz seçim!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public void handleShowReaderHistory() {
        System.out.println("\n=== Okuyucu Geçmişi ===");
        Map<String, Reader> readers = controller.getAllReaders();

        if (readers.isEmpty()) {
            System.out.println("Kayıtlı okuyucu bulunmamaktadır.");
            return;
        }

        handleShowAllReaders();

        System.out.print("\nGeçmişini görüntülemek istediğiniz okuyucunun ID'sini girin: ");
        String readerId = scanner.nextLine();

        try {
            controller.showReaderHistory(readerId);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }


    public void handleReturnBook() {
        System.out.println("\n=== Kitap İadesi Al ===");

        handleShowAllReaders();

        System.out.print("\nİade yapacak okuyucunun ID'sini girin: ");
        String readerId = scanner.nextLine();

        try {
            Reader reader = controller.getAllReaders().get(readerId);
            if (reader == null) {
                System.out.println("Hata: Okuyucu bulunamadı!");
                return;
            }

            Set<Book> borrowedBooks = reader.getBorrowedBooks();
            if (borrowedBooks.isEmpty()) {
                System.out.println("Bu okuyucunun iade edecek kitabı yok!");
                return;
            }

            System.out.println("\nOkuyucunun ödünç aldığı kitaplar:");
            borrowedBooks.forEach(book -> {
                System.out.println("------------------------");
                System.out.println("ID: " + book.getBookID());
                System.out.println("Başlık: " + book.getTitle());
                System.out.println("Yazar: " + book.getAuthor());
            });

            System.out.print("\nİade edilecek kitabın ID'sini girin: ");
            String bookId = scanner.nextLine();

            controller.returnBook(bookId, readerId);
            System.out.println("Kitap başarıyla iade alındı!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public void handleCalculateFine() {
        System.out.println("\n=== Ceza Hesapla ve Tahsil Et ===");

        handleShowAllReaders();

        System.out.print("\nCeza hesaplanacak okuyucunun ID'sini girin (örn: 001): ");
        String readerId = scanner.nextLine();

        try {
            if (!readerId.startsWith("OKUYUCU")) {
                int idNumber = Integer.parseInt(readerId);
                readerId = "OKUYUCU - " + String.format("%03d", idNumber);
            }

            controller.calculateAndCollectFine(readerId);
        } catch (NumberFormatException e) {
            System.out.println("Hata: Geçersiz ID formatı! Lütfen sadece sayı girin (örn: 001)");
        }
    }

    public void handleLendBook() {
        System.out.println("\n=== Ödünç Kitap Ver ===");

        showAvailableBooks();

        if (controller.getAvailableBooks().isEmpty()) {
            return;
        }

        System.out.print("\nÖdünç verilecek kitabın ID'sini girin: ");
        String bookId = scanner.nextLine();

        handleShowAllReaders();

        System.out.print("\nKitabı alacak okuyucunun ID'sini girin: ");
        String readerId = scanner.nextLine();

        try {
            controller.lendBook(bookId, readerId);
            System.out.println("Kitap başarıyla ödünç verildi!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public void handleMostReadBooks() {
        System.out.println("\n=== En Çok Okunan Kitaplar ===");
        List<Book> mostReadBooks = controller.getMostReadBooks(5);

        if (mostReadBooks.isEmpty()) {
            System.out.println("Henüz hiç kitap ödünç alınmamış.");
            return;
        }

        mostReadBooks.forEach(book -> {
            System.out.println("\n------------------------");
            System.out.println("ID: " + book.getBookID());
            System.out.println("Başlık: " + book.getTitle());
            System.out.println("Yazar: " + book.getAuthor());
            System.out.println("Okunma Sayısı: " + book.getBorrowCount());
        });
    }

    public void handleMostActiveReaders() {
        System.out.println("\n=== En Aktif Okuyucular ===");
        List<Reader> activeReaders = controller.getMostActiveReaders(5);

        if (activeReaders.isEmpty()) {
            System.out.println("Henüz aktif okuyucu bulunmamaktadır.");
            return;
        }

        activeReaders.forEach(reader -> {
            System.out.println("\n------------------------");
            System.out.println("ID: " + reader.getReaderId());
            System.out.println("Ad: " + reader.getName());
            System.out.println("Ödünç Alınan Kitap Sayısı: " + reader.getBorrowedBooks().size());
        });
    }

    public void handleOverdueBooks() {
        System.out.println("\n=== Gecikmiş Kitaplar ===");
        List<Book> overdueBooks = controller.getOverdueBooks();

        if (overdueBooks.isEmpty()) {
            System.out.println("Gecikmiş kitap bulunmamaktadır.");
            return;
        }

        overdueBooks.forEach(book -> {
            System.out.println("\n------------------------");
            System.out.println("ID: " + book.getBookID());
            System.out.println("Başlık: " + book.getTitle());
            System.out.println("Yazar: " + book.getAuthor());
            System.out.println("Durum: " + book.getStatus().getDisplayName());
        });
    }

    public void handleBookStatistics() {
        System.out.println("\n=== Kitap İstatistikleri ===");
        Map<String, Integer> stats = controller.getBookStatistics();

        System.out.println("\n------------------------");
        stats.forEach((key, value) ->
                System.out.println(key + ": " + value)
        );
        System.out.println("------------------------");
    }
}
