package model;

public enum Category {
    LIFESTYLE("Lifestyle"),
    FIXED_EXPENSES("Fixed expenses"),
    SHOPPING("Shopping"),
    SAVINGS("Savings"),
    OTHER("Other"),
    EXTRA_INCOME("Extra income");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
