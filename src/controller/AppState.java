package controller;

import model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppState {

    private static final String[] predefinedCategories = {
        "Lifestyle", "Fixed expenses", "Shopping", "Savings", "Other", "Extra income"
    };

    private final Account mainAccount = new Account();
    private final Map<String, Account> categoryAccounts = new HashMap<>();

    public AppState() {
        for (String category : predefinedCategories) {
            categoryAccounts.put(category, new Account());
        }
    }

    public Account getAccount() {
        return mainAccount;
    }

    public Account getCategoryAccount(String category) {
        return categoryAccounts.get(category);
    }

    public List<String> getCategoryNames() {
        return Arrays.asList(predefinedCategories);
    }

    public Transaction processTransaction(String description, String amountText, String category) {
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
        if (category.equals("Extra income")) {
            transaction = new Income(amount, LocalDate.now(), description, category);
        } else {
            transaction = new Expense(amount, LocalDate.now(), description, category);
        }

        mainAccount.addTransaction(transaction);
        Account categoryAccount = categoryAccounts.get(category);
        if (categoryAccount != null) {
            categoryAccount.addTransaction(transaction);
        }

        return transaction;
    }
}
