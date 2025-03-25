package main.model.book.builder;

import main.model.book.Journals;
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
            throw new IllegalArgumentException("Dergi konusu boş veya null olamaz");
        }
        this.journalSubject = subject;
        return this;
    }

    public JournalBuilder issn(String issn) {
        if (issn == null || !issn.matches("\\d{4}-\\d{3}[\\dX]")) {
            throw new IllegalArgumentException("Geçersiz ISSN biçimi. XXXX-XXXX olmalıdır");
        }
        this.issn = issn;
        return this;
    }

    public JournalBuilder volume(int volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Cilt sayısı pozitif olmalıdır");
        }
        this.volume = volume;
        return this;
    }

    public JournalBuilder issue(int issue) {
        if (issue <= 0) {
            throw new IllegalArgumentException("Sayı pozitif olmalıdır");
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

        if (journalSubject == null) missingFields.add("dergi konusu");
        if (issn == null) missingFields.add("ISSN");
        if (volume <= 0) missingFields.add("cilt");
        if (issue <= 0) missingFields.add("sayı");

        if (!missingFields.isEmpty()) {
            throw new IllegalStateException(
                    "Dergi oluşturulamıyor. Gerekli alanlar eksik: " +
                            String.join(", ", missingFields)
            );
        }
    }

    public String getJournalSubject() { return journalSubject; }
    public String getIssn() { return issn; }
    public int getVolume() { return volume; }
    public int getIssue() { return issue; }
}