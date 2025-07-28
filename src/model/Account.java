package model;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Account {
    private double balance;
    private List<Transaction> transactionHistory;

    public Account() {
        this.balance = 100.0;
        this.transactionHistory = new ArrayList<>();
    }

    public void addTransaction(Transaction t) {
        transactionHistory.add(t);
        balance += t.getAmount();
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getRecentTransactions(int limit) {
        int fromIndex = Math.max(0, transactionHistory.size() - limit);
        return transactionHistory.subList(fromIndex, transactionHistory.size());
    }

    public List<Transaction> getByDate(LocalDate date){
        List<Transaction> selectedTransactions = new ArrayList<>();
        for (int i = 0; i < transactionHistory.size(); i++) {
            if(transactionHistory.get(i).getDate() == date){
                selectedTransactions.add(transactionHistory.get(i));
            }
        }
        return selectedTransactions;
    }
}
