package main.java.model.person;

import main.java.model.book.Book;
import main.java.model.book.enums.BookStatus;
import main.java.model.person.enums.ReaderLimit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reader extends Person{
    private final ReaderLimit readerLimit;
    private final List<Book> books;

    public Reader(String name, ReaderLimit readerLimit) {
        super(name);
        this.readerLimit = readerLimit;
        this.books = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        if (books.size() >= readerLimit.getMaxBooks()) {
            throw new IllegalStateException(
                    String.format("Cannot borrow more than %d books with %s limit",
                            readerLimit.getMaxBooks(),
                            readerLimit.name())
            );
        }

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException(
                    String.format("Book '%s' is not available for borrowing. Current status: %s",
                            book.getTitle(),
                            book.getStatus().name())
            );
        }

        books.add(book);
        book.updateStatus(BookStatus.BORROWED);
        System.out.println(
                String.format("Book '%s' borrowed successfully. Remaining limit: %d books",
                        book.getTitle(),
                        readerLimit.getMaxBooks() - books.size())
        );
    }

    public void returnBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        if (!books.remove(book)) {
            throw new IllegalStateException(
                    String.format("Book '%s' was not borrowed by this reader", book.getTitle())
            );
        }

        book.updateStatus(BookStatus.AVAILABLE);
        System.out.println(String.format("Book '%s' returned successfully", book.getTitle()));
    }

    public void showBook() {
        if (books.isEmpty()) {
            System.out.println("No books currently borrowed by: " + getName());
            return;
        }

        System.out.println("Books borrowed by " + getName() + ":");
        books.forEach(Book::display);
    }

    @Override
    public String whoyouare() {
        return String.format("Reader: %s (Books borrowed: %d/%d)",
                getName(), books.size(), readerLimit.getMaxBooks());
    }

    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(books);
    }

    public int getRemainingBookLimit() {
        return readerLimit.getMaxBooks() - books.size();
    }
}
