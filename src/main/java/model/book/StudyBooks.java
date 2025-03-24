package main.java.model.book;

import main.java.model.book.builder.StudyBookBuilder;
import main.java.model.book.enums.BookStatus;
import main.java.model.book.interfaces.IReadable;
import main.java.model.book.interfaces.IBorrowable;

public class StudyBooks extends Book {
    private final String subject;
    private final String isbn;
    private final int grade;
    private final String publisher;

    public StudyBooks(StudyBookBuilder builder) {
        super(builder);
        this.subject = builder.getSubject();
        this.isbn = builder.getIsbn();
        this.grade = builder.getGrade();
        this.publisher = builder.getPublisher();
    }

    public String getSubject() {
        return subject;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getGrade() {
        return grade;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public void display() {
        super.display();
        System.out.println(String.format("""
            Study Book Details:
            Subject: %s
            ISBN: %s
            Grade: %d
            Publisher: %s
            """,
                subject, isbn, grade, publisher));
    }

    @Override
    public String getDisplayInfo() {
        return super.getDisplayInfo() + String.format("""
            
            Study Book Details:
            Subject: %s
            ISBN: %s
            Grade: %d
            Publisher: %s""",
                subject, isbn, grade, publisher);
    }

    @Override
    public String toString() {
        return String.format("StudyBook{id='%s', name='%s', subject='%s', grade=%d, publisher='%s'}",
                getBookID(), getTitle(), subject, grade, publisher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyBooks)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return getBookID().hashCode();
    }
}