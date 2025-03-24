package main.controller;

import main.java.model.book.Book;
import main.java.model.book.builder.BookBuilder;
import main.java.model.library.Library;

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
}