package main.controller;


import main.model.book.Book;
import main.model.book.Journals;
import main.model.book.builder.BookBuilder;
import main.model.book.builder.JournalBuilder;
import main.model.book.builder.StudyBookBuilder;
import main.model.book.enums.BookStatus;
import main.model.library.Library;
import main.model.person.Reader;
import main.model.person.enums.ReaderLimit;
import main.model.record.MemberBuilder;
import main.model.record.MemberRecord;

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

    public void addNewReader(String name, String address, String phoneNo, ReaderLimit readerLimit) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Okuyucu adı boş olamaz!");
        }

        String memberId = "OKUYUCU-" + String.format("%03d", library.getReaders().size() + 1);

        Reader newReader = new Reader(name, readerLimit);

        library.addReader(newReader);
    }

    public void deleteReader(String readerId) {
        if (readerId == null || readerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Okuyucu ID'si boş olamaz!");
        }

        Reader reader = library.getReader(readerId);
        if (!reader.getBorrowedBooks().isEmpty()) {
            throw new IllegalStateException("Okuyucunun iade etmediği kitaplar var!");
        }
        library.deleteReader(readerId);
    }

    public void updateReader(String readerId, String newName, String newAddress,
                             String newPhoneNo, ReaderLimit newReaderLimit) {
        if (readerId == null || readerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Okuyucu ID'si boş olamaz!");
        }

        library.updateReader(readerId, newName, newAddress, newPhoneNo, newReaderLimit);
    }

    public Map<String, Reader> getAllReaders() {
        return library.getReaders();
    }

    public void showReaderHistory(String readerId) {
        if (readerId == null || readerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Okuyucu ID'si boş olamaz!");
        }

        Reader reader = library.getReader(readerId);
        reader.showBook();
    }

    public void lendBook(String bookId, String readerId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap ID'si boş olamaz!");
        }
        if (readerId == null || readerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Okuyucu ID'si boş olamaz!");
        }

        library.lendBook(bookId, readerId);
    }

    public void returnBook(String bookId, String readerId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new IllegalArgumentException("Kitap ID'si boş olamaz!");
        }
        if (readerId == null || readerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Okuyucu ID'si boş olamaz!");
        }

        library.takeBackBook(bookId, readerId);
    }

    public void calculateAndCollectFine(String readerId) {
        if (readerId == null || readerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Okuyucu ID'si boş olamaz!");
        }

        Reader reader = library.getReader(readerId);
        double fine = 0.0;

        if (fine > 0) {
            reader.payFine(fine);
        }
    }

    
}