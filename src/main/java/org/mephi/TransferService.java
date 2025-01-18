package org.mephi;

/**
 * Интерфейс TransferService обеспечивает функциональность для упрощения
 * денежных переводов между пользователями в рамках системы.
 * Класс-реализатор должен убедиться, что у отправителя достаточно средств
 * в кошельке, прежде чем инициировать перевод. Кроме того, он должен
 * проверить наличие пользователя-получателя.
 * Ожидается, что реализации этого интерфейса будут обрабатывать перевод
 * процесс обновления кошельков отправителя и получателя, включая
 * добавление соответствующих транзакций в их истории транзакций.
 */
public interface TransferService {

    /**
     * Переводит деньги с одного аккаунта пользователя на другой в рамках системы.
     * Метод гарантирует, что у отправителя достаточно средств на кошельке,
     * и проверяет наличие получателя.
     *
     * @param sender пользователь, инициирующий денежный перевод
     * @param recipientUsername имя пользователя-получателя
     * @param amount денежная сумма, подлежащая переводу
     */
    void transferMoney(User sender, String recipientUsername, double amount);
}
