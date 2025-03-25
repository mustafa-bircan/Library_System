package main.controller;


import main.model.book.Book;
import main.model.book.builder.BookBuilder;
import main.model.book.builder.JournalBuilder;
import main.model.book.builder.StudyBookBuilder;
import main.model.book.enums.BookStatus;
import main.model.library.Library;
import main.model.person.Faculty;
import main.model.person.Reader;
import main.model.person.Student;
import main.model.person.builder.FacultyBuilder;
import main.model.person.builder.StudentBuilder;
import main.model.person.enums.ReaderLimit;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

        Book newBook = new BookBuilder(title, author)
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

    public void addNewJournal(String title, String author, double price, String edition, String subject, String issn, int volume, int issue) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Dergi adı boş olamaz!");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Yazar adı boş olamaz!");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz!");
        }

        Book newJournal = new JournalBuilder(title, author)
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
        double totalFine = 0.0;
        final double DAILY_FINE = 5.0;

        for (Book book : reader.getBorrowedBooks()) {
            if (book.isOverdue()) {
                long overdueDays = book.getDueDate().until(LocalDate.now()).getDays();
                if (overdueDays > 0) {
                    double bookFine = overdueDays * DAILY_FINE;
                    totalFine += bookFine;
                    System.out.printf("""
                                    Kitap: %s
                                    Gecikme: %d gün
                                    Ceza: ₺%.2f
                                    %n""",
                            book.getTitle(), overdueDays, bookFine);
                }
            }
        }

        if (totalFine > 0) {
            reader.payFine(totalFine);
        } else {
            System.out.println("Ödenmesi gereken ceza bulunmamaktadır.");
        }
    }

    public List<Book> getMostReadBooks(int limit) {
        Map<String, Book> allBooks = library.getBooks();
        return allBooks.values().stream()
                .sorted((b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Reader> getMostActiveReaders(int limit) {
        Map<String, Reader> allReaders = library.getReaders();
        return allReaders.values().stream()
                .sorted((r1, r2) -> Integer.compare(r2.getBorrowedBooks().size(), r1.getBorrowedBooks().size()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Book> getOverdueBooks() {
        Map<String, Book> allBooks = library.getBooks();
        return allBooks.values().stream()
                .filter(book -> book.getStatus() == BookStatus.BORROWED)
                .filter(Book::isOverdue)
                .collect(Collectors.toList());
    }

    public Map<String, Integer> getBookStatistics() {
        Map<String, Book> allBooks = library.getBooks();
        Map<String, Integer> stats = new HashMap<>();

        stats.put("Toplam Kitap", allBooks.size());
        stats.put("Müsait Kitap", (int) allBooks.values().stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE).count());
        stats.put("Ödünç Verilmiş", (int) allBooks.values().stream()
                .filter(book -> book.getStatus() == BookStatus.BORROWED).count());
        stats.put("Bakımda", (int) allBooks.values().stream()
                .filter(book -> book.getStatus() == BookStatus.MAINTENANCE).count());

        return stats;
    }

    public void addNewStudent(String name, String department, int year) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Öğrenci adı boş olamaz!");
        }
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Bölüm adı boş olamaz!");
        }
        if (year < 1 || year > 4) {
            throw new IllegalArgumentException("Sınıf 1-4 arasında olmalıdır!");
        }

        String studentId = generateStudentNumber();

        Student student = new StudentBuilder()
                .setName(name)
                .setStudentId(studentId)
                .setDepartment(department)
                .setYear(year)
                .build();

        library.addReader(student);
        System.out.println("Öğrenci başarıyla kaydedildi. Öğrenci Numarası: " + studentId);
    }

    public void addNewFaculty(String name, String department, String title) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Akademisyen adı boş olamaz!");
        }
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Bölüm adı boş olamaz!");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Unvan boş olamaz!");
        }

        String facultyId = generateFacultyId();

        Faculty faculty = new FacultyBuilder()
                .setName(name)
                .setFacultyId(facultyId)
                .setDepartment(department)
                .setTitle(title)
                .build();

        library.addReader(faculty);
        System.out.println("Akademisyen başarıyla kaydedildi. Akademisyen ID: " + facultyId);
    }

    public List<Student> getAllStudents() {
        return library.getReaders().values().stream()
                .filter(reader -> reader instanceof Student)
                .map(reader -> (Student) reader)
                .collect(Collectors.toList());
    }

    public List<Faculty> getAllFaculty() {
        return library.getReaders().values().stream()
                .filter(reader -> reader instanceof Faculty)
                .map(reader -> (Faculty) reader)
                .collect(Collectors.toList());
    }

    public void updateStudent(String studentId, String newName, String newDepartment, int newYear) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Öğrenci ID boş olamaz!");
        }

        Reader reader = library.getReader(studentId);
        if (reader == null) {
            throw new IllegalArgumentException("Bu ID'ye sahip bir okuyucu bulunamadı!");
        }

        if (!(reader instanceof Student student)) {
            throw new IllegalArgumentException("Bu ID bir öğrenciye ait değil!");
        }

        String currentStudentId = student.getStudentId();

        student.setName(newName);
        student.setDepartment(newDepartment);
        student.setYear(newYear);

        student.setStudentId(currentStudentId);
    }

    public void updateFaculty(String facultyId, String newName, String newDepartment, String newTitle) {
        if (facultyId == null || facultyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Akademisyen ID boş olamaz!");
        }

        Reader reader = library.getReader(facultyId);
        if (reader == null) {
            throw new IllegalArgumentException("Bu ID'ye sahip bir okuyucu bulunamadı!");
        }

        if (!(reader instanceof Faculty faculty)) {
            throw new IllegalArgumentException("Bu ID bir akademisyene ait değil!");
        }

        String currentFacultyId = faculty.getFacultyId();

        faculty.setName(newName);
        faculty.setDepartment(newDepartment);
        faculty.setTitle(newTitle);

        faculty.setFacultyId(currentFacultyId);
    }

    public void deleteStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Öğrenci ID boş olamaz!");
        }

        Reader reader = library.getReader(studentId);
        if (!(reader instanceof Student)) {
            throw new IllegalArgumentException("Bu ID bir öğrenciye ait değil!");
        }

        library.deleteReader(studentId);
    }

    public void deleteFaculty(String facultyId) {
        if (facultyId == null || facultyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Akademisyen ID boş olamaz!");
        }

        Reader reader = library.getReader(facultyId);
        if (!(reader instanceof Faculty)) {
            throw new IllegalArgumentException("Bu ID bir akademisyene ait değil!");
        }

        library.deleteReader(facultyId);
    }

    public List<Student> getStudentsByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Bölüm adı boş olamaz!");
        }

        return getAllStudents().stream()
                .filter(student -> student.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    private String generateFacultyId() {
        int nextId = getAllFaculty().size() + 1;
        return "AKD-" + String.format("%03d", nextId);
    }

    private String generateStudentNumber() {
        int nextId = getAllStudents().size() + 1;
        return "STD-" + String.format("%03d", nextId);
    }
}