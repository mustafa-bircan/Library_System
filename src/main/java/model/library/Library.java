package main.java.model.library;

import main.java.model.book.Book;
import main.java.model.book.enums.BookStatus;
import main.java.model.person.Reader;

import java.util.*;

public class Library {
    private final Map<String, Book> books;
    private final Map<String, Reader> readers;


    public Library() {
        this.books = new HashMap<>();
        this.readers = new HashMap<>();
    }

    public Book getBook(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap ID boş veya null olamaz");
        }
        Book book = books.get(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Kitap ID ile bulunamadı: " + bookId);
        }
        return book;
    }

    public Reader getReader(String readerId) {
        if (readerId == null || readerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Okuyucu ID boş veya null olamaz");
        }
        Reader reader = readers.get(readerId);
        if (reader == null) {
            throw new IllegalStateException("Okuyucu bulunamadı: " + readerId);
        }
        return reader;
    }

    public void newBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Kitap boş olamaz");
        }

        String bookId = book.getBookID();
        if (books.containsKey(bookId)) {
            throw new IllegalStateException("Kitap zaten bu ID ile mevcut: " + bookId);
        }

        books.put(bookId, book);
    }

    public void lendBook(String bookId, String readerId) {
        Book book = getBook(bookId);
        Reader reader = getReader(readerId);

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Kitap ödünç verilemez: " + book.getTitle());
        }

        reader.borrowBook(book);
        System.out.println("Kitap başarıyla " + reader.getName().toUpperCase() + " adlı okuyucumuza ödünç verildi :)");
    }

    public void takeBackBook(String bookId, String readerId) {
        Book book = getBook(bookId);
        Reader reader = getReader(readerId);

        reader.returnBook(book);
        System.out.println("Kitap başarıyla " + reader.getName().toUpperCase() + " adlı okuyucumuzdan iade alındı :)");
    }

    public void showBook() {
        if (books.isEmpty()) {
            System.out.println("Kütüphanede kitap yok!");
            return;
        }

        System.out.println("Kütüphane Kitap Envanteri:");
        books.values().forEach(book -> {
            System.out.println("------------------------");
            book.display();
        });
    }

    public void addReader(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Okuyucu boş olamaz");
        }

        String readerId = reader.getReaderId();
        if (readers.containsKey(readerId)) {
            throw new IllegalStateException("Okuyucu zaten mevcut ID ile kayıtlı: " + readerId);
        }

        readers.put(readerId, reader);
        System.out.println("Yeni okuyucu eklendi: " + reader.getName() + " (ID: " + readerId + ")");
    }

    public Set<Book> getAvailableBooks() {
        return books.values().stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    public Map<String, Book> getBooks() {
        return Collections.unmodifiableMap(books);
    }

    public Map<String, Reader> getReaders() {
        return Collections.unmodifiableMap(readers);
    }
    @Override
    public String toString() {
        return String.format("Kütüphane{books=%d, okuyucular=%d}", books.size(), readers.size());
    }
}
