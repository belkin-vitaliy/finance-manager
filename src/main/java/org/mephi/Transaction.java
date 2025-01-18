package org.mephi;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

/**
 * Класс транзакций представляет собой финансовую транзакцию, которая может быть доходом или расходом.
 * В нем отображаются такие сведения, как тип транзакции, сумма, категория, дата и необязательное описание.
 */
@Getter
@Setter
public class Transaction {
    private TransactionType type;
    private double amount;
    private Category category;
    private Date date;
    private String description;

    public Transaction(double amount, Category category, String description) {
        this.amount = amount;
        this.category = category;
        this.description = description;

        if (Objects.isNull(date)) {
            this.date = new Date();
        }
    }

    public Transaction(TransactionType type, double amount, Category category, Date date, String description) {
        this.type = type;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
}
