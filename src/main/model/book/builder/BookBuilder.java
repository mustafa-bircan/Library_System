package main.model.book.builder;

import main.model.book.Book;
import java.time.LocalDate;

public class BookBuilder {
    private String author;
    private String name;
    private double price;
    private String edition;
    private LocalDate dateOfPurchase;
    private String journalSubject;
    private String subject;
    private String issn;
    private String isbn;
    private int volume;
    private int issue;
    private int grade;
    private String publisher;

    public BookBuilder(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public BookBuilder price(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz");
        }
        this.price = price;
        return this;
    }

    public BookBuilder edition(String edition) {
        if (edition == null || edition.trim().isEmpty()) {
            throw new IllegalArgumentException("Edition null veya boş olamaz");
        }
        this.edition = edition;
        return this;
    }

    public BookBuilder dateOfPurchase(LocalDate dateOfPurchase) {
        if (dateOfPurchase != null && dateOfPurchase.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Satın alma tarihi gelecekte bir tarih olamaz");
        }
        this.dateOfPurchase = dateOfPurchase;
        return this;
    }

    public BookBuilder journalSubject(String subject) {
        this.journalSubject = subject;
        return this;
    }

    public BookBuilder issn(String issn) {
        this.issn = issn;
        return this;
    }

    public BookBuilder volume(int volume) {
        this.volume = volume;
        return this;
    }

    public BookBuilder issue(int issue) {
        this.issue = issue;
        return this;
    }

    public BookBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public BookBuilder isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookBuilder grade(int grade) {
        this.grade = grade;
        return this;
    }

    public BookBuilder publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Book build() {
        validateFields();
        return new Book(this);
    }

    private void validateFields() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap adı boş veya null olamaz");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Yazar adı boş veya null olamaz");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz");
        }
        if (edition == null || edition.trim().isEmpty()) {
            throw new IllegalArgumentException("Edition null veya boş olamaz");
        }
    }

    public String getName() { return name; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public String getEdition() { return edition; }
    public LocalDate getDateOfPurchase() { return dateOfPurchase; }

    public String getJournalSubject() { return journalSubject; }
    public String getSubject() { return subject; }
    public String getIssn() { return issn; }
    public String getIsbn() { return isbn; }
    public int getVolume() { return volume; }
    public int getIssue() { return issue; }
    public int getGrade() { return grade; }
    public String getPublisher() { return publisher; }
}