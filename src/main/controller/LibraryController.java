package main.controller;


import main.model.book.Book;
import main.model.book.Journals;
import main.model.book.builder.BookBuilder;
import main.model.book.builder.JournalBuilder;
import main.model.book.builder.StudyBookBuilder;
import main.model.book.enums.BookStatus;
import main.model.library.Library;

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

    public void updateBookStatus(String bookId, BookStatus newStatus) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap ID'si boş olamaz!");
        }
        if (newStatus == null) {
            throw new IllegalArgumentException("Yeni durum boş olamaz!");
        }

        Book book = library.getBook(bookId);
        if (book.getStatus() == BookStatus.BORROWED) {
            throw new IllegalStateException("Ödünç verilmiş kitabın durumu değiştirilemez!");
        }

        book.updateStatus(newStatus);
    }

    public void addNewJournal(String title, String author, double price, String edition,String subject, String issn, int volume, int issue) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Dergi adı boş olamaz!");
        }
        if (author == null || author.trim().isEmpty()){
            throw new IllegalArgumentException("Yazar adı boş olamaz!");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz!");
        }

        Book newJournal = new JournalBuilder(title,author)
                .price(price)
                .edition(edition)
                .journalSubject(subject)
                .issn(issn)
                .volume(volume)
                .issue(issue)
                .build();

        library.newBook(newJournal);
    }

    public void addNewStudyBook(String title, String author, double price, String edition,
                                String subject, String isbn, int grade, String publisher) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap adı boş olamaz!");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Yazar adı boş olamaz!");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz!");
        }
        Book newStudyBook = new StudyBookBuilder(title, author)
                .price(price)
                .edition(edition)
                .subject(subject)
                .isbn(isbn)
                .grade(grade)
                .publisher(publisher)
                .build();

        library.newBook(newStudyBook);
    }
}