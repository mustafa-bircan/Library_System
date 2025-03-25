package main.model.person.enums;

public enum ReaderLimit {
    STANDART(5,14,0.50),
    VIP(10,21,0.25),
    RESTRICTED(2,7,1.00);

    private final int maxBooks;
    private final int maxDays;
    private final double finePerDay;

    ReaderLimit(int maxBooks, int maxDays, double finePerDay) {
        this.maxBooks = maxBooks;
        this.maxDays = maxDays;
        this.finePerDay = finePerDay;
    }

    public int getMaxBooks() {
        return maxBooks;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public double getFinePerDay() {
        return finePerDay;
    }
}
