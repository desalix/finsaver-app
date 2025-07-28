package model;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Account {
    private double balance;
    private List<Transaction> transactions;

    public Account() {
        this.balance = 100.0;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
        balance += t.getAmount();
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Transaction> getRecentTransactions(int limit) {
        int fromIndex = Math.max(0, transactions.size() - limit);
        return transactions.subList(fromIndex, transactions.size());
    }

    public List<Transaction> getByDate(LocalDate date){
        List<Transaction> selectedTransactions = new ArrayList<>();
        for (int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getDate() == date){
                selectedTransactions.add(transactions.get(i));
            }
        }
        return selectedTransactions;
    }
}
