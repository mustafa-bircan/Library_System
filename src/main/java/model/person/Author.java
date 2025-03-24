package main.java.model.person;

import main.java.model.book.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Author extends Person{
    private final List<Book> books;

    public Author(String name) {
        super(name);
        this.books = new ArrayList<>();
    }

    public void newBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        books.add(book);
    }

    public List<Book> showBooks() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public String whoyouare() {
        return "Author: " + getName();
    }

    public int getBookCount() {
        return books.size();
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + getName() + '\'' +
                ", numberOfBooks=" + books.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return getName().equals(author.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
