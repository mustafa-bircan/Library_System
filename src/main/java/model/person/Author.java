package main.java.model.person;

import main.java.model.book.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Author extends Person{
    private final List<Book> books;

    public Author(String name) {
        super(name);
        this.books = new ArrayList<>();
    }

    public void newBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Kitap boş olamaz");
        }
        books.add(book);
    }

    public void showBook() {
        if (books.isEmpty()) {
            System.out.println("Yazar için kitap mevcut değil: " + getName());
            return;
        }

        System.out.println("Yazara göre kitaplar " + getName() + ":");
        books.forEach(Book::display);
    }

    @Override
    public String whoyouare() {
        return "Yazar: " + getName();
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);  
    }

    public int getBookCount() {
        return books.size();
    }
    @Override
    public String toString() {
        return "Yazar{" +
                "ad='" + getName() + '\'' +
                ", kitapSayisi=" + books.size() +
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
