package controller;

import java.util.List;
import model.*;

public class TransactionController {

    private final AppState appState;

    public TransactionController(AppState appState) {
        this.appState = appState;
    }

    public Transaction addTransaction(String description, String amountText, Category category) {
        return appState.processTransaction(description, amountText, category);
    }

    public double getBalance() {
        return appState.getAccount().getBalance();
    }

    public List<String> getCategoryNames() {
        return appState.getCategoryNames();
    }
} 
