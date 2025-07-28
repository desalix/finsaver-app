package model;
import java.time.LocalDate;

public class Income extends Transaction {
    public Income(double amount, LocalDate date, Category category, String description) {
        super(Math.abs(amount), date, category, description);
    }
}
