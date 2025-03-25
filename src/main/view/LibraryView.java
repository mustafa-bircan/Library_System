package main.view;

import main.controller.LibraryController;

import java.util.Scanner;

public class LibraryView {
    private final LibraryController controller;
    private final Scanner scanner;

    public LibraryView(LibraryController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("\n=== KÜTÜPHANE YÖNETİM SİSTEMİ ===");
        System.out.println("=== KİTAP İŞLEMLERİ ===");
        System.out.println("1. Tüm Kitapları Göster");
        System.out.println("2. Boşta Olan Kitapları Göster");
        System.out.println("3. Yeni Kitap Ekle");
        System.out.println("4. Kitap Sil");
        System.out.println("5. Kitap Bilgilerini Güncelle");
        System.out.println("6. Kitap Durumunu Güncelle");
        System.out.println("7. Dergi Ekle");
        System.out.println("8. Ders Kitabı Ekle");

        System.out.println("\n=== ÜYE İŞLEMLERİ ===");
        System.out.println("9. Yeni Okuyucu Ekle");
        System.out.println("10. Okuyucu Sil");
        System.out.println("11. Okuyucu Bilgilerini Güncelle");
        System.out.println("12. Tüm Okuyucuları Göster");
        System.out.println("13. Okuyucu Geçmişini Görüntüle");

        System.out.println("\n=== ÖDÜNÇ İŞLEMLERİ ===");
        System.out.println("14. Ödünç Kitap Ver");
        System.out.println("15. Kitap İadesi Al");
        System.out.println("16. Ceza Hesapla ve Tahsil Et");

        System.out.println("\n=== RAPORLAMA ===");
        System.out.println("17. En Çok Okunan Kitaplar");
        System.out.println("18. En Aktif Okuyucular");
        System.out.println("19. Gecikmiş Kitaplar");
        System.out.println("20. Kitap İstatistikleri");

        System.out.println("\n0. ÇIKIŞ");
        System.out.print("\nSeçim İçin Tuşlama Yapın: ");
    }

    public int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
