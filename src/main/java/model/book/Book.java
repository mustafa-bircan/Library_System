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
    private LocalDate dateOfPurchase;

    private Book(BookBuilder builder) {
        if (builder.name == null || builder.name.trim().isEmpty()) {
            throw new IllegalArgumentException("Book name cannot be null or empty");
        }
        if (builder.author == null || builder.author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }
        if (builder.price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (builder.edition == null || builder.edition.trim().isEmpty()) {
            throw new IllegalArgumentException("Edition cannot be null or empty");
        }

        this.bookID = UUID.randomUUID().toString();
        this.author = builder.author;
        this.name = builder.name;
        this.price = builder.price;
        this.edition = builder.edition;
        this.dateOfPurchase = builder.dateOfPurchase != null ? builder.dateOfPurchase : LocalDate.now();
        this.status = BookStatus.AVAILABLE;
    }

    public static class BookBuilder {
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
            return new Book(this);
        }
    }

    public String getTitle() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getOwner() {
        return status.getDisplayName();
    }

    public void changeOwner(String newOwner) {
        if (newOwner == null || newOwner.trim().isEmpty()) {
            throw new IllegalArgumentException("New owner cannot be null or empty");
        }
        this.status = BookStatus.BORROWED;
    }

    public void updateStatus(BookStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = newStatus;
    }

    public void display() {
        System.out.println(getDisplayInfo());
    }

    public String getBookID() {
        return bookID;
    }

    public double getPrice() {
        return price;
    }

    public BookStatus getStatus() {
        return status;
    }

    public String getDisplayInfo() {
        return new StringBuilder()
                .append("Book ID: ").append(bookID).append("\n")
                .append("Title: ").append(name).append("\n")
                .append("Author: ").append(author).append("\n")
                .append("Price: $").append(String.format("%.2f", price)).append("\n")
                .append("Edition: ").append(edition).append("\n")
                .append("Status: ").append(status.getDisplayName()).append("\n")
                .append("Purchase Date: ").append(dateOfPurchase)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return bookID.equals(book.bookID);
    }

    @Override
    public int hashCode() {
        return bookID.hashCode();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + bookID + '\'' +
                ", title='" + name + '\'' +
                ", author='" + author + '\'' +
                ", status=" + status +
                '}';
    }
}
