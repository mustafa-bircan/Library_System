package main.java.model.book;

import main.java.model.book.builder.BookBuilder;
import main.java.model.book.enums.BookStatus;
import main.java.model.book.interfaces.IBorrowable;
import main.java.model.book.interfaces.IReadable;

import java.time.LocalDate;
import java.util.UUID;

public class Book implements IReadable, IBorrowable {
    private final String bookID;
    private String author;
    private String name;
    private double price;
    private BookStatus status;
    private String edition;
    private LocalDate dateOfPurchase;

     public Book(BookBuilder builder) {
        this.bookID = UUID.randomUUID().toString();
        this.author = builder.getAuthor();
        this.name = builder.getName();
        this.price = builder.getPrice();
        this.edition = builder.getEdition();
        this.dateOfPurchase = builder.getDateOfPurchase() != null ?
                builder.getDateOfPurchase() : LocalDate.now();
        this.status = BookStatus.AVAILABLE;
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