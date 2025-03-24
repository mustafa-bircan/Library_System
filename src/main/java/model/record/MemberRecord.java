package main.java.model.record;

import main.java.model.book.Book;
import main.java.model.person.enums.ReaderLimit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemberRecord {
    private final String memberId;
    private final ReaderLimit type;
    private final LocalDate dateOfMembership;
    private int noBooksIssued;
    private final int maxBookLimit;
    private final String name;
    private final String address;
    private final String phoneNo;
    private final List<Book> issuedBooks;

    MemberRecord(MemberBuilder builder) {
        this.memberId = builder.getMemberId();
        this.name = builder.getName();
        this.address = builder.getAddress();
        this.phoneNo = builder.getPhoneNo();
        this.type = builder.getType();

        this.dateOfMembership = LocalDate.now();
        this.maxBookLimit = this.type.getMaxBooks();
        this.noBooksIssued = 0;
        this.issuedBooks = new ArrayList<>();
    }

    public void incBookIssued() {
        if (noBooksIssued >= maxBookLimit) {
            throw new IllegalArgumentException(
                    String.format("Cannot issue more books. Limit reached: %d/%d",
                            noBooksIssued,maxBookLimit)
            );
        }
    }

    public void decBookIssued() {
        if (noBooksIssued <= 0) {
            throw new IllegalStateException("No books currently issued");
        }
        noBooksIssued--;
    }

    public void payBill(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative");
        }
        System.out.println(String.format("""
            =====================
            PAYMENT RECEIPT
            =====================
            Member: %s
            Member ID: %s
            Amount Paid: $%.2f
            Date: %s
            =====================
            """,
                name, memberId, amount, LocalDate.now()));
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhoneNo() { return phoneNo; }
    public ReaderLimit getType() { return type; }
    public LocalDate getDateOfMembership() { return dateOfMembership; }
    public int getNoBooksIssued() { return noBooksIssued; }
    public int getMaxBookLimit() { return maxBookLimit; }
    public List<Book> getIssuedBooks() { return Collections.unmodifiableList(issuedBooks); }


    @Override
    public String toString() {
        return String.format("MemberRecord{id='%s', name='%s', type=%s, booksIssued=%d/%d}",
                memberId, name, type, noBooksIssued, maxBookLimit);
    }
}
