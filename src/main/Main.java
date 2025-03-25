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
                default -> System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }
    }
}