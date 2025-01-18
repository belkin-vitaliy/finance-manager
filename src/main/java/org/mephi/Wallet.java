package org.mephi;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс Wallet представляет финансовый кошелек пользователя, который содержит баланс,
 * список транзакций и бюджеты по категориям. Он предоставляет методы управления
 * финансовыми транзакциями, отслеживания доходов и расходов и обеспечения соблюдения бюджетных ограничений для
 * различных категорий.
 */
@Getter
@Setter
public class Wallet {
    public static final String BUDGET_LIMIT_EXCEEDED_MESSAGE = "Превышен лимит бюджета по категории '";

    private double balance;
    private List<Transaction> transactions;
    private Map<String, Category> categories;

    public Wallet() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.categories = new HashMap<>();
    }

    /**
     * Устанавливает бюджет для указанной категории. Если категория уже существует, её бюджет обновляется.
     * Если категории не существует, создается новая категория с указанным бюджетом.
     *
     * @param categoryName название категории, для которой устанавливается бюджет
     * @param budgetAmount сумма бюджета для заданной категории
     */
    public void setBudgetForCategory(String categoryName, double budgetAmount) {
        if (this.categories.containsKey(categoryName)) {
            this.categories.get(categoryName).setBudget(budgetAmount);
        } else {
            Category newCategory = new Category(categoryName, budgetAmount);
            this.categories.put(categoryName, newCategory);
        }
    }

    /**
     * Проверяет, превышает ли общая сумма расходов в конкретной категории лимит бюджета для этой категории.
     * Если лимит превышен, выводится предупреждающее сообщение.
     *
     * @param categoryName название категории, которую нужно сопоставить с ее бюджетным лимитом
     */
    public void checkBudgetLimit(String categoryName) {
        if (this.categories.containsKey(categoryName)) {
            Category category = this.categories.get(categoryName);
            double totalSpent = transactions.stream()
                    .filter(t -> t.getAmount() < 0 && t.getCategory().getName().equals(categoryName))
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            if (Math.abs(totalSpent) > category.getBudget()) {
                System.out.println(BUDGET_LIMIT_EXCEEDED_MESSAGE + categoryName + "'.");
            }
        }
    }

}
