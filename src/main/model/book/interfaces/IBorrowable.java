package main.model.book.interfaces;

import main.model.book.enums.BookStatus;

public interface IBorrowable {
    String getOwner();
    void changeOwner(String newOwner);
    BookStatus getStatus();
    void updateStatus(BookStatus newStatus);
}
