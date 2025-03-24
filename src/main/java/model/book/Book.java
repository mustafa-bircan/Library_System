package main.java.model.book;

import main.java.model.book.enums.BookStatus;

import java.time.LocalDate;
import java.util.UUID;

public class Book {
    private final String bookID;
    private String author;
    private String name;
    private double price;
    private BookStatus status;
    private String edition;
    private LocalDate purchaseDate;


    private Book(BookBuilder builder) {
        this.bookID = UUID.randomUUID().toString();  // Unique ID generation
        this.author = builder.author;
        this.name = builder.name;
        this.price = builder.price;
        this.edition = builder.edition;
        this.purchaseDate = builder.purchaseDate;
        this.status = BookStatus.AVAILABLE;  // Default enum value
    }

    public static class BookBuilder {
        private String author;
        private String name;
        private double price;
        private String edition;
        private LocalDate purchaseDate;

        public BookBuilder(String name, String author) {
            this.name = name;
            this.author = author;
        }

        public BookBuilder price(double price) {
            this.price = price;
            return this;
        }

        public BookBuilder edition(String edition) {
            this.edition = edition;
            return this;
        }

        public BookBuilder purchaseDate(LocalDate purchaseDate) {
            this.purchaseDate = purchaseDate;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public BookStatus getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public void updateStatus(BookStatus newStatus) {
        this.status = newStatus;
    }

    public String getDisplayInfo() {
        StringBuilder info = new StringBuilder()
                .append("Book ID: ").append(bookID).append("\n")
                .append("Title: ").append(name).append("\n")
                .append("Author: ").append(author).append("\n")
                .append("Price: ").append(price).append("\n")
                .append("Edition: ").append(edition).append("\n")
                .append("Status: ").append(status).append("\n")
                .append("Purchase Date: ").append(purchaseDate);
        return info.toString();
    }
}
