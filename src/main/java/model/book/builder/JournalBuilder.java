package main.java.model.book.builder;

import main.java.model.book.Journals;
import java.util.ArrayList;
import java.util.List;

public class JournalBuilder extends BookBuilder {
    private String journalSubject;
    private String issn;
    private int volume;
    private int issue;

    public JournalBuilder(String name, String author) {
        super(name, author);
    }

    public JournalBuilder journalSubject(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Journal subject cannot be null or empty");
        }
        this.journalSubject = subject;
        return this;
    }

    public JournalBuilder issn(String issn) {
        if (issn == null || !issn.matches("\\d{4}-\\d{3}[\\dX]")) {
            throw new IllegalArgumentException("Invalid ISSN format. Must be XXXX-XXXX");
        }
        this.issn = issn;
        return this;
    }

    public JournalBuilder volume(int volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume must be positive");
        }
        this.volume = volume;
        return this;
    }

    public JournalBuilder issue(int issue) {
        if (issue <= 0) {
            throw new IllegalArgumentException("Issue must be positive");
        }
        this.issue = issue;
        return this;
    }

    @Override
    public Journals build() {
        validateJournalFields();
        return new Journals(this);
    }

    private void validateJournalFields() {
        List<String> missingFields = new ArrayList<>();

        if (journalSubject == null) missingFields.add("journal subject");
        if (issn == null) missingFields.add("ISSN");
        if (volume <= 0) missingFields.add("volume");
        if (issue <= 0) missingFields.add("issue");

        if (!missingFields.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot build Journal. Missing required fields: " +
                            String.join(", ", missingFields)
            );
        }
    }

    public String getJournalSubject() { return journalSubject; }
    public String getIssn() { return issn; }
    public int getVolume() { return volume; }
    public int getIssue() { return issue; }
}