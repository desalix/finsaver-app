package model;
import java.time.LocalDate;

public class Expense extends Transaction {
    public Expense(double amount, LocalDate date, String category, String description) {
        super(-Math.abs(amount), date, category, description);
    }
}
