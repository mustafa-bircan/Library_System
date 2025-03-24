package main.java.model.book.interfaces;

import main.java.model.book.enums.BookStatus;

public interface IBorrowable {
    String getOwner();
    void changeOwner(String newOwner);
    BookStatus getStatus();
    void updateStatus(BookStatus newStatus);
}
