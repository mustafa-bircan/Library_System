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
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }
        return books
                .stream()
                .filter(book -> book.getBookID().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));
    }

    public Reader getReader(String readerName) {
        if (readerName == null || readerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Reader nsame cannot be null or empty");
        }
        return readers
                .stream()
                .filter(reader -> reader.getName().equals(readerName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Reader not found: " + readerName));
    }

    public void newBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        if (books.stream().anyMatch(b -> b.getBookID().equals(book.getBookID()))) {
            throw new IllegalStateException("Book already exists with ID: " + book.getBookID());
        }

        books.add(book);
        System.out.println("New book added: " + book.getTitle());
    }

    public void lendBook(String bookId, String readerName) {
        Book book = getBook(bookId);
        Reader reader = getReader(readerName);

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not available for lending: " + book.getTitle());
        }

        reader.borrowBook(book);
        System.out.println("Book lent successfully to " + reader.getName());
    }

    public void takeBackBook(String bookId, String readerName) {
        Book book = getBook(bookId);
        Reader reader = getReader(readerName);

        reader.returnBook(book);
        System.out.println("Book returned successfully by " + reader.getName());
    }

    public void showBook() {
        if (books.isEmpty()) {
            System.out.println("No books in the library");
            return;
        }

        System.out.println("Library Book Inventory:");
        books.forEach(book -> {
            System.out.println("------------------------");
            book.display();
        });
    }

    public void addReader(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null");
        }

        if (readers.stream().anyMatch(r -> r.getName().equals(reader.getName()))) {
            throw new IllegalStateException("Reader already exists: " + reader.getName());
        }

        readers.add(reader);
        System.out.println("New reader added: " + reader.getName());
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
        return String.format("Library{books=%d, readers=%d}", books.size(), readers.size());
    }
}
