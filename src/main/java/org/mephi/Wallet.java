package org.mephi;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Wallet {
    private double balance;
    private List<Transaction> transactions;
    private Map<String, Category> categories;

    public Wallet() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.categories = new HashMap<>();
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        this.balance += transaction.getAmount();
        String categoryName = transaction.getCategory().getName();
        if (!this.categories.containsKey(categoryName)) {
            this.categories.put(categoryName, transaction.getCategory());
        }
        checkBudgetLimit(categoryName);
    }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return Math.abs(transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .mapToDouble(Transaction::getAmount)
                .sum());
    }

    public void setBudgetForCategory(String categoryName, double budgetAmount) {
        if (this.categories.containsKey(categoryName)) {
            this.categories.get(categoryName).setBudget(budgetAmount);
        } else {
            Category newCategory = new Category(categoryName, budgetAmount);
            this.categories.put(categoryName, newCategory);
        }
    }

    public void checkBudgetLimit(String categoryName) {
        if (this.categories.containsKey(categoryName)) {
            Category category = this.categories.get(categoryName);
            double totalSpent = transactions.stream()
                    .filter(t -> t.getAmount() < 0 && t.getCategory().getName().equals(categoryName))
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            if (Math.abs(totalSpent) > category.getBudget()) {
                System.out.println("Превышен лимит бюджета по категории '" + categoryName + "'.");
            }
        }
    }

}
