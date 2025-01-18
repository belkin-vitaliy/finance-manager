package org.mephi;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

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
