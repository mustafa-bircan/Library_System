package main.controller;

import main.java.model.book.Book;
import main.java.model.book.builder.BookBuilder;
import main.java.model.book.enums.BookStatus;
import main.java.model.library.Library;

import java.util.Map;
import java.util.Set;

public class LibraryController {
    private final Library library;

    public LibraryController(Library library) {
        this.library = library;
    }

    public void addNewBook(String title, String author, double price, String edition) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap adı boş olamaz!");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Yazar adı boş olamaz!");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz!");
        }

        Book newBook = new BookBuilder(title,author)
                .price(price)
                .edition(edition)
                .build();

        library.newBook(newBook);
    }

    public Map<String, Book> getAllBooks() {
        return library.getBooks();
    }

    public Set<Book> getAvailableBooks() {
        return library.getAvailableBooks();
    }

    public void deleteBook(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap ID'si boş olamaz!");
        }

        Book book = library.getBook(bookId);
        if (book.getStatus() == BookStatus.BORROWED) {
            throw new IllegalStateException("Ödünç verilmiş kitap silinemez");
        }
        library.deleteBook(bookId);
    }

    public void updateBook(String bookId, String newTitle, String newAuthor, double newPrice, String newEdition) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap ID'si boş olamaz!");
        }
        library.updateBook(bookId, newTitle, newAuthor, newPrice, newEdition);
    }
}