package model;

public enum Category {
    LIFESTYLE("Lifestyle"),
    FIXED_EXPENSES("Fixed expenses"),
    SHOPPING("Shopping"),
    SAVINGS("Savings"),
    OTHER("Other"),
    EXTRA_INCOME("Extra income"),
    MONTHLY_INCOME("Monthly income");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Category fromDisplayName(String name) {
        for (Category c : values()) {
            if (c.getDisplayName().equals(name)) return c;
        }
        throw new IllegalArgumentException("Unknown category name: " + name);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
