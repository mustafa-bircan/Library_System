package main.java.model.book.enums;

public enum BookStatus {
        AVAILABLE("Available"),
        BORROWED("Borrowed"),
        MAINTENANCE("Under Maintenance"),
        LOST("Lost");

        private final String displayName;

        BookStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }


