package main.java.model.library;

import main.java.model.book.Book;
import main.java.model.book.enums.BookStatus;
import main.java.model.person.Reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Library {
    private final List<Book> books;
    private final List<Reader> readers;

    public Library() {
        this.books = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public Book getBook(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap ID boş veya null olamaz");
        }
        return books
                .stream()
                .filter(book -> book.getBookID().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Kitap ID ile bulunamadı: " + bookId));
    }

    public Reader getReader(String readerName) {
        if (readerName == null || readerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Okuyucu adı boş veya null olamaz");
        }
        return readers
                .stream()
                .filter(reader -> reader.getName().equals(readerName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Okuyucu bulunamadı: " + readerName));
    }

    public void newBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Kitap boş olamaz");
        }

        if (books.stream().anyMatch(b -> b.getBookID().equals(book.getBookID()))) {
            throw new IllegalStateException("Kitap zaten bu ID ile mevcut: " + book.getBookID());
        }

        books.add(book);
    }

    public void lendBook(String bookId, String readerName) {
        Book book = getBook(bookId);
        Reader reader = getReader(readerName);

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Kitap ödünç verilemez: " + book.getTitle());
        }

        reader.borrowBook(book);
        System.out.println("Kitap başarıyla ödünç verildi " + reader.getName());
    }

    public void takeBackBook(String bookId, String readerName) {
        Book book = getBook(bookId);
        Reader reader = getReader(readerName);

        reader.returnBook(book);
        System.out.println("Kitap başarıyla iade edildi: " + reader.getName());
    }

    public void showBook() {
        if (books.isEmpty()) {
            System.out.println("Kütüphanede kitap yok!");
            return;
        }

        System.out.println("Kütüphane Kitap Envanteri:");
        books.forEach(book -> {
            System.out.println("------------------------");
            book.display();
        });
    }

    public void addReader(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Okuyucu boş olamaz");
        }

        if (readers.stream().anyMatch(r -> r.getName().equals(reader.getName()))) {
            throw new IllegalStateException("Okuyucu zaten mevcut: " + reader.getName());
        }

        readers.add(reader);
        System.out.println("Yeni okuyucu eklendi: " + reader.getName());
    }

    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
                .toList();
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public List<Reader> getReaders() {
        return Collections.unmodifiableList(readers);
    }

    @Override
    public String toString() {
        return String.format("Kütüphane{books=%d, okuyucular=%d}", books.size(), readers.size());
    }
}
