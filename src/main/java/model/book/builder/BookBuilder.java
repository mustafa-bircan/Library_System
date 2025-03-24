package main.java.model.book.builder;

import main.java.model.book.Book;
import java.time.LocalDate;

public class BookBuilder {
    private String author;
    private String name;
    private double price;
    private String edition;
    private LocalDate dateOfPurchase;

    public BookBuilder(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public BookBuilder price(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
        return this;
    }

    public BookBuilder edition(String edition) {
        if (edition == null || edition.trim().isEmpty()) {
            throw new IllegalArgumentException("Edition cannot be null or empty");
        }
        this.edition = edition;
        return this;
    }

    public BookBuilder dateOfPurchase(LocalDate dateOfPurchase) {
        if (dateOfPurchase != null && dateOfPurchase.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Purchase date cannot be in the future");
        }
        this.dateOfPurchase = dateOfPurchase;
        return this;
    }

    public Book build() {
        validateFields();
        return new Book(this);
    }

    private void validateFields() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Book name cannot be null or empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (edition == null || edition.trim().isEmpty()) {
            throw new IllegalArgumentException("Edition cannot be null or empty");
        }
    }

    public String getName() { return name; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public String getEdition() { return edition; }
    public LocalDate getDateOfPurchase() { return dateOfPurchase; }
}