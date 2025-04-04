package main.model.person;

import main.model.book.Book;
import main.model.book.enums.BookStatus;
import main.model.person.enums.ReaderLimit;

import java.time.LocalDate;
import java.util.*;

public class Reader extends Person{
    private final ReaderLimit readerLimit;
    private final Set<Book> books;
    private final String readerId;
    private static int nextId = 1;

    public Reader(String name, ReaderLimit readerLimit) {
        super(name);
        this.readerLimit = readerLimit;
        this.books = new HashSet<>();
        this.readerId = "K" + String.format("%03d", nextId++);
    }

    public void borrowBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Kitap boş olamaz");
        }

        if (books.size() >= readerLimit.getMaxBooks()) {
            throw new IllegalStateException(
                    String.format("%s limitiyle %d'den fazla kitap ödünç alınamaz",
                            readerLimit.getMaxBooks(),
                            readerLimit.name())
            );
        }

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException(
                    String.format("'%s' kitabı ödünç alınamıyor. Mevcut durum: %s",
                            book.getTitle(),
                            book.getStatus().name())
            );
        }

        books.add(book);
        book.updateStatus(BookStatus.BORROWED);
        System.out.println(
                String.format("'%s' kitabı başarıyla ödünç alındı. Kalan sınır: %d kitap",
                        book.getTitle(),
                        readerLimit.getMaxBooks() - books.size())
        );
    }

    public void returnBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Kitap boş olamaz");
        }

        if (!books.remove(book)) {
            throw new IllegalStateException(
                    String.format("'%s' kitabı bu okuyucu tarafından ödünç alınamadı", book.getTitle())
            );
        }

        book.updateStatus(BookStatus.AVAILABLE);
        System.out.println(String.format("'%s' kitabı başarıyla iade alındı", book.getTitle()));
    }

    public void showBook() {
        if (books.isEmpty()) {
            System.out.println("Şu anda ödünç alınmış kitap yok: " + getName());
            return;
        }

        System.out.println("Ödünç alınan kitaplar " + getName() + ":");
        books.forEach(Book::display);
    }

    public void payFine(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Ceza tutarı 0'dan büyük olmalıdır!");
        }
        System.out.println(String.format("""
        =====================
        CEZA ÖDEME MAKBUZU
        =====================
        Okuyucu: %s
        Okuyucu ID: %s
        Ödenen Tutar: ₺%.2f
        Tarih: %s
        =====================
        """,
                getName(), getReaderId(), amount, LocalDate.now()));
    }

    @Override
    public String whoyouare() {
        return String.format("Okuyucu: %s (ID: %s) (Ödünç alınan kitaplar: %d/%d)",
                getName(), books.size(), readerLimit.getMaxBooks());
    }

    public Set<Book> getBorrowedBooks() {
        return Collections.unmodifiableSet(books);
    }

    public String getReaderId() {
        return readerId;
    }

    public ReaderLimit getReaderLimit() {
        return readerLimit;
    }

    public int getRemainingBookLimit() {
        return readerLimit.getMaxBooks() - books.size();
    }
}
