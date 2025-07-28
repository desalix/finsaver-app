package controller;

import model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppState {

    private final Account mainAccount = new Account();
    private final Map<Category, Account> categoryAccounts = new HashMap<>();

    public AppState() {
        for (Category category : Category.values()) {
            categoryAccounts.put(category, new Account());
        }
    }

    public Account getAccount() {
        return mainAccount;
    }

    public Account getCategoryAccount(Category category) {
        return categoryAccounts.get(category);
    }

    public List<String> getCategoryNames() {
        return Arrays.stream(Category.values()).map(Category::getDisplayName).collect(Collectors.toList());
    }

    public Transaction processTransaction(String description, String amountText, Category category) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        if (category == null) {
            throw new IllegalArgumentException("Please select a category.");
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount.");
        }

        Transaction transaction;
        if (category.equals(Category.EXTRA_INCOME)) {
            transaction = new Income(amount, LocalDate.now(), category, description);
        } else {
            transaction = new Expense(amount, LocalDate.now(), category, description);
        }   

        mainAccount.addTransaction(transaction);
        Account categoryAccount = categoryAccounts.get(category);

        if (categoryAccount != null) {
            categoryAccount.addTransaction(transaction);
        }

        return transaction;
    }
}
