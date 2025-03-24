package main.java.model.book;

import main.java.model.book.builder.JournalBuilder;
import main.java.model.book.enums.BookStatus;

public class Journals extends Book {
        private final String journalSubject;
        private final String issn;
        private final int volume;
        private final int issue;

        public Journals(JournalBuilder builder) {
                super(builder);
                this.journalSubject = builder.getJournalSubject();
                this.issn = builder.getIssn();
                this.volume = builder.getVolume();
                this.issue = builder.getIssue();
        }

        public String getJournalSubject() {
                return journalSubject;
        }

        public String getIssn() {
                return issn;
        }

        public int getVolume() {
                return volume;
        }

        public int getIssue() {
                return issue;
        }

        @Override
        public void display() {
                super.display();
                System.out.println(String.format("""
            Journal Details:
            Subject: %s
            ISSN: %s
            Volume: %d
            Issue: %d
            """,
                        journalSubject, issn, volume, issue));
        }

        @Override
        public String toString() {
                return String.format("Journal{id='%s', name='%s', subject='%s', volume=%d, issue=%d}",
                        getBookID(), getTitle(), journalSubject, volume, issue);
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Journals)) return false;
                return super.equals(o);
        }

        @Override
        public int hashCode() {
                return getBookID().hashCode();
        }
}