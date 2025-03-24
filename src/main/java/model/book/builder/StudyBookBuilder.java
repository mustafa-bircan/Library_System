package main.java.model.book.builder;

import main.java.model.book.StudyBooks;

import java.util.ArrayList;
import java.util.List;

public class StudyBookBuilder extends BookBuilder{
    private String subject;
    private String isbn;
    private int grade;
    private String publisher;

    public StudyBookBuilder(String name, String author) {
        super(name, author);
    }

    public StudyBookBuilder subject(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Konu boş veya null olamaz");
        }
        this.subject = subject;
        return this;
    }

    public StudyBookBuilder isbn(String isbn) {
        if (isbn == null || !isbn.matches("\\d{3}-\\d{10}")) {
            throw new IllegalArgumentException("Geçersiz ISBN biçimi. XXX-XXXXXXXXXX olmalıdır");
        }
        this.isbn = isbn;
        return this;
    }

    public StudyBookBuilder grade(int grade) {
        if (grade < 1 || grade > 12) {
            throw new IllegalArgumentException("Sınıf 1 ile 12 arasında olmalıdır");
        }
        this.grade = grade;
        return this;
    }

    public StudyBookBuilder publisher(String publisher) {
        if (publisher == null || publisher.trim().isEmpty()) {
            throw new IllegalArgumentException("Yayıncı boş veya null olamaz");
        }
        this.publisher = publisher;
        return this;
    }

    @Override
    public StudyBooks build() {
        validateStudyBookFields();
        return new StudyBooks(this);
    }

    private void validateStudyBookFields() {
        List<String> missingFields = new ArrayList<>();

        if (subject == null) missingFields.add("konu");
        if (isbn == null) missingFields.add("ISBN");
        if (grade < 1 || grade > 12) missingFields.add("sınıf");
        if (publisher == null) missingFields.add("yayıncı");

        if (!missingFields.isEmpty()) {
            throw new IllegalStateException(
                    "Çalışma Kitabı oluşturulamıyor. Gerekli alanlar eksik: " +
                            String.join(", ", missingFields)
            );
        }
    }

    public String getSubject() { return subject; }
    public String getIsbn() { return isbn; }
    public int getGrade() { return grade; }
    public String getPublisher() { return publisher; }
}
