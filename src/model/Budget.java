package model;

import java.util.EnumMap;
import java.util.Map;

public class Budget {
    private final Map<Category, Double> categoryLimits = new EnumMap<>(Category.class);

    public void setLimit(Category category, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Budget amount must be positive.");
        }
        categoryLimits.put(category, amount);
    }

    public double getLimit(Category category) {
        return categoryLimits.getOrDefault(category, 0.0);
    }

    public Map<Category, Double> getAllLimits() {
        return new EnumMap<>(categoryLimits); // Return a copy to protect internal data
    }

    public boolean hasLimit(Category category) {
        return categoryLimits.containsKey(category);
    }
}
