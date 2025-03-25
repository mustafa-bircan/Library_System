package main.model.book.enums;

import main.model.book.Book;
import main.model.book.builder.BookBuilder;
import main.model.book.builder.JournalBuilder;
import main.model.book.builder.StudyBookBuilder;

import java.time.LocalDate;

public enum DefaultBooks {
    BILIM_TEKNIK(
            new JournalBuilder("Bilim ve Teknik", "TÜBİTAK")
                    .journalSubject("Bilim")
                    .issn("1234-5678")
                    .volume(55)
                    .issue(652)
                    .price(25.00)
                    .edition("2024")
                    .dateOfPurchase(LocalDate.now())
    ),
    POPULER_BILIM(
            new JournalBuilder("Popüler Bilim", "Doğan Burda")
                    .journalSubject("Bilim ve Teknoloji")
                    .issn("2345-6789")
                    .volume(27)
                    .issue(315)
                    .edition("2024")
                    .dateOfPurchase(LocalDate.now())
    ),

    JAVA_PROGRAMLAMA(
            new StudyBookBuilder("Java ile Programlamaya Giriş", "Ahmet Yılmaz")
                    .subject("Bilgisayar Programlama")
                    .isbn("123-1234567890")
                    .grade(1)
                    .publisher("Kodlab")
                    .price(120.00)
                    .edition("3")
                    .dateOfPurchase(LocalDate.now())
    ),

    VERI_YAPILARI(
            new StudyBookBuilder("Veri Yapıları ve Algoritmalar", "Mehmet Öztürk")
                    .subject("Bilgisayar Bilimleri")
                    .isbn("234-2345678901")
                    .grade(2)
                    .publisher("Seçkin")
                    .price(150.00)
                    .edition("2")
                    .dateOfPurchase(LocalDate.now())
    ),

    TUTUNAMAYANLAR(
            new BookBuilder("Tutunamayanlar", "Oğuz Atay")
                    .price(85.00)
                    .edition("İletişim Klasikleri")
                    .dateOfPurchase(LocalDate.now())
    ),

    KUYUCAKLI_YUSUF(
            new BookBuilder("Kuyucaklı Yusuf", "Sabahattin Ali")
                    .price(45.00)
                    .edition("Yapı Kredi Yayınları")
                    .dateOfPurchase(LocalDate.now())
    ),

    INCE_MEMED(
            new BookBuilder("İnce Memed", "Yaşar Kemal")
                    .price(95.00)
                    .edition("Yapı Kredi Yayınları")
                    .dateOfPurchase(LocalDate.now())
    );

    private final BookBuilder builder;

    DefaultBooks(BookBuilder builder) {
        this.builder = builder;
    }

    public Book createBook() {
        return builder.build();
    }
}
