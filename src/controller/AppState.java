package controller;

import model.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppState {

    private final Account mainAccount = new Account();
    private final Map<Category, Account> categoryAccounts = new HashMap<>();
    private double monthlyIncomeAmount;
    private LocalDate lastIncomeDate;
    private final Budget currentBudget = new Budget();



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
        return Arrays.stream(Category.values())
            .filter(c -> c != Category.MONTHLY_INCOME)
            .map(Category::getDisplayName)
            .collect(Collectors.toList());
    }

    public double getCategoryBalance(Category category) {
        Account account = categoryAccounts.get(category);
        return account != null ? account.getBalance() : 0.0;
    }

    public List<Transaction> getCategoryTransactions(Category category) {
        Account account = categoryAccounts.get(category);
        return account != null ? account.getTransactions() : Collections.emptyList();
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

    public void setMonthlyIncome(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Income must be positive.");
        this.monthlyIncomeAmount = amount;
    }

    public void applyMonthlyIncomeIfNeeded() {
        LocalDate today = LocalDate.now();
        if (today.getDayOfMonth() == 1 && (lastIncomeDate == null || lastIncomeDate.getMonthValue() != today.getMonthValue())) {
            Income income = new Income(
                monthlyIncomeAmount,
                today,
                Category.MONTHLY_INCOME,
                "Monthly income"
            );
            mainAccount.addTransaction(income);
            categoryAccounts.get(Category.MONTHLY_INCOME).addTransaction(income);
            lastIncomeDate = today;
        }
    }

    public void setBudgetLimit(Category category, double amount) {
        currentBudget.setLimit(category, amount);
    }

    public MonthlySummary generateSummary(YearMonth month) {
        MonthlySummary summary = new MonthlySummary();

        for (Transaction t : mainAccount.getTransactions()) {
            if (YearMonth.from(t.getDate()).equals(month)) {
                if (t instanceof Income) {
                    summary.addIncome(t.getAmount());
                } else {
                    summary.addExpense(t.getAmount());
                }
                summary.addToCategory(t.getCategory(), t.getSignedAmount());
            }
        }
        return summary;
    }


    public MonthlySummary generateSummaryWithBudget(YearMonth month) {
        MonthlySummary summary = generateSummary(month);
        summary.setBudget(currentBudget);
        return summary;
    }
}
