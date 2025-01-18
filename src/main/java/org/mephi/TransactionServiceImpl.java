package org.mephi;

import java.util.Objects;

public class TransactionServiceImpl implements TransactionService {

    public static final String EMPTY_WALLET_ERROR_MESSAGE = "Кошелек не может быть пустым";
    public static final String ERROR_WALLET_TRANSACTION_NULL = "Кошелек и транзакция не могут быть нулевыми";

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
}