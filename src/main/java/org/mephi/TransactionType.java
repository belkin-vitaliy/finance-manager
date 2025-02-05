package org.mephi;

public enum TransactionType {

    /**
     * Представляет собой тип транзакции, которая увеличивает финансовый баланс
     * кошелька, например, за счет заработной платы, премий или других видов дохода.
     */
    INCOME,

    /**
     * Представляет собой тип транзакции, которая уменьшает финансовый баланс кошелька пользователя.
     * Этот тип связан с расходами, такими как покупки, услуги или любые другие
     * финансовые операции, приводящие к списанию средств.
     */
    EXPENSE
}
