package main;

import main.model.book.enums.DefaultBooks;
import main.model.library.Library;
import main.controller.LibraryController;
import main.view.LibraryView;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        System.out.println("Kütüphanede bulunan kitaplar:");
        System.out.println("------------------------");
        for (DefaultBooks defaultBooks : DefaultBooks.values()) {
            library.newBook(defaultBooks.createBook());
            System.out.println("- " + defaultBooks.createBook().getTitle());
        }
        System.out.println("------------------------");

        LibraryController controller = new LibraryController(library);
        LibraryView view = new LibraryView(controller);

        while (true) {
            view.showMenu();
            int choice = view.getChoice();

            if (choice == 0) {
                System.out.println("Kütüphane Yönetim Sistemini Kullandığınız İçin Teşekkür Ederiz!");
                break;
            }

            switch (choice) {
                case 1 -> view.showAllBooks();
                case 2 -> view.showAvailableBooks();
                case 3 -> view.handleNewBook();
                case 4 -> view.handleDeleteBook();
                case 5 -> view.handleUpdateBook();
                case 6 -> view.handleUpdateBookStatus();
                case 7 -> view.handleNewJournal();
                case 8 -> view.handleNewStudyBook();
                case 9 -> view.handleNewReader();
                case 10 -> view.handleDeleteReader();
                case 11 -> view.handleUpdateReader();
                case 12 -> view.handleShowAllReaders();
                case 13 -> view.handleShowReaderHistory();
                case 14 -> view.handleLendBook();
                case 15 -> view.handleReturnBook();
                case 16 -> view.handleCalculateFine();
                case 17 -> view.handleMostReadBooks();
                case 18 -> view.handleMostActiveReaders();
                case 19 -> view.handleOverdueBooks();
                case 20 -> view.handleBookStatistics();
                case 21 -> view.handleNewStudentRegistration();
                case 22 -> view.handleNewFacultyRegistration();
                case 23 -> view.showAllStudents();
                case 24 -> view.showAllFaculty();
                case 25 -> view.handleUpdateStudent();
                case 26 -> view.handleUpdateFaculty();
                case 27 -> view.handleDeleteStudent();
                case 28 -> view.handleDeleteFaculty();
                case 29 -> view.handleListStudentsByDepartment();
                case 30 -> view.handleSearchBook();
                default -> System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }
    }
}