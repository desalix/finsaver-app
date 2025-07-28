package model;
import java.time.LocalDate;

public abstract class Transaction {
    protected double amount;
    protected LocalDate date;
    protected String category;
    protected String description;

    public Transaction(double amount, LocalDate date, String description, String category) {
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

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
