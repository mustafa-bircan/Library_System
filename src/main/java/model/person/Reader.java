package main.java.model.person;

import main.java.model.book.Book;
import main.java.model.book.enums.BookStatus;
import main.java.model.person.enums.ReaderLimit;

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
        this.readerId = "OKUYUCU - " + String.format("%03d", nextId++);
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
