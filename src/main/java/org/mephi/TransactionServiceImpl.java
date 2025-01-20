package org.mephi;

import java.util.Objects;

public class TransactionServiceImpl implements TransactionService {

    public static final String EMPTY_WALLET_ERROR_MESSAGE = "Кошелек не может быть пустым";
    public static final String ERROR_WALLET_TRANSACTION_NULL = "Кошелек и транзакция не могут быть нулевыми";
    public static final String NULL_SENDER_RECIPIENT_MESSAGE = "Отправитель и получатель не могут иметь значения null.";
    public static final String ERROR_MESSAGE_INVALID_AMOUNT = "Сумма должна быть больше нуля.";
    public static final String CATEGORY_NULL_ERROR_MESSAGE = "Категория не должна быть нулевой.";

    @Override
    public void addTransaction(Wallet wallet, Transaction transaction) {
        if (Objects.isNull(wallet) || Objects.isNull(transaction)) {
            throw new IllegalArgumentException(ERROR_WALLET_TRANSACTION_NULL);
        }

        wallet.getTransactions().add(transaction);
        wallet.setBalance(wallet.getBalance() + transaction.getAmount());

        String categoryName = transaction.getCategory().getName();
        addCategoryIfAbsent(wallet, categoryName, transaction.getCategory());

        wallet.checkBudgetLimit(categoryName);
    }

    @Override
    public double getTotalIncome(Wallet wallet) {
        if (wallet == null) {
            throw new IllegalArgumentException(EMPTY_WALLET_ERROR_MESSAGE);
        }

        return wallet.getTransactions().stream()
                .filter(transaction -> transaction.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public double getTotalExpense(Wallet wallet) {
        if (wallet == null) {
            throw new IllegalArgumentException(EMPTY_WALLET_ERROR_MESSAGE);
        }

        return wallet.getTransactions().stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .mapToDouble(Transaction::getAmount)
                .map(Math::abs)
                .sum();
    }

    private void addCategoryIfAbsent(Wallet wallet, String categoryName, Category category) {
        if (!wallet.getCategories().containsKey(categoryName)) {
            wallet.getCategories().put(categoryName, category);
        }
    }

    @Override
    public void logTransaction(User sender, User recipient, double amount, CategoryType category) {
        if (sender == null || recipient == null) {
            throw new IllegalArgumentException(NULL_SENDER_RECIPIENT_MESSAGE);
        }
        if (amount <= 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_AMOUNT);
        }
        if (category == null) {
            throw new IllegalArgumentException(CATEGORY_NULL_ERROR_MESSAGE);
        }


        //TODO Create a transaction record
        //Transaction transaction = new Transaction(sender.getUsername(), recipient.getUsername(), amount, category, new Date());

        //TODO Save the transaction (in memory or to database)
        //transactions.add(transaction);

        // Optionally log the transaction to a console or logger
        System.out.printf("Transaction logged: %s -> %s, Amount: %.2f, Category: %s%n",
                sender.getUsername(), recipient.getUsername(), amount, category);
    }

}