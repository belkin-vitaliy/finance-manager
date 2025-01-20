package org.mephi;

/**
 * Интерфейс TransactionService предоставляет методы для управления финансовыми транзакциями
 * в кошельке пользователя. Он обеспечивает добавление транзакций, обновление баланса,
 * управление категориями и проверку лимитов бюджета.
 */
public interface TransactionService {

    /**
     * Добавляет транзакцию в кошелек. Обновляет баланс кошелька,
     * управляет связанной с ним категорией и проверяет лимит бюджета.
     *
     * @param wallet      кошелек, на который будет добавлена транзакция
     * @param transaction транзакция, которую нужно добавить
     */
    void addTransaction(Wallet wallet, Transaction transaction);

    /**
     * Рассчитывает общий доход для данного кошелька на основе положительных сумм транзакций.
     *
     * @param wallet кошелек, для которого рассчитывается общий доход
     * @return общий доход
     */
    double getTotalIncome(Wallet wallet);

    /**
     * Рассчитывает общую сумму расходов для данного кошелька на основе отрицательных сумм транзакций.
     *
     * @param wallet кошелек, для которого рассчитываются общие расходы
     * @return общие расходы
     */
    double getTotalExpense(Wallet wallet);

    public void logTransaction(User sender, User recipient, double amount, CategoryType category);

}