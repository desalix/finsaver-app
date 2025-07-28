package model;
import java.time.LocalDate;

public abstract class Transaction {
    protected double amount;
    protected LocalDate date;
    protected Category category;
    protected String description;

    public Transaction(double amount, LocalDate date, Category category, String description) {
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
    
    public double getSignedAmount() {
        return this instanceof Expense ? -amount : amount;
    }

}
