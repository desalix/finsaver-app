package model;

import java.util.EnumMap;
import java.util.Map;

public class MonthlySummary {
    private Budget budget;
    private final Map<Category, Double> categoryTotals = new EnumMap<>(Category.class);
    private double totalIncome;
    private double totalExpenses;

    public void addToCategory(Category category, double amount) {
        categoryTotals.merge(category, amount, Double::sum);
    }
    
    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public double getNetBalance() {
        return totalIncome - totalExpenses;
    }

    public void addIncome(double amount) {
        totalIncome += amount;
    }

    public void addExpense(double amount) {
        totalExpenses += amount;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public double getRemaining(Category category) {
        if (budget == null) return 0;
        double spent = Math.abs(categoryTotals.getOrDefault(category, 0.0));
        return budget.getLimit(category) - spent;
    }

    public boolean isOverBudget(Category category) {
        return getRemaining(category) < 0;
    }
}
