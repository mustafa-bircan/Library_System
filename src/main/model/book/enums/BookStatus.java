package main.model.book.enums;

public enum BookStatus {
        AVAILABLE("Mevcut"),
        BORROWED("Ödünç"),
        MAINTENANCE("Restore"),
        LOST("Kayıp");

        private final String displayName;

        BookStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }


