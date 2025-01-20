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

    /**
     * Представляет финансовый баланс кошелька.
     * Это значение указывает текущую доступную сумму денег
     * в кошельке, учитывая все транзакции доходов и расходов.
     */
    private double balance;
    /**
     * Представляет собой набор транзакций, связанных с кошельком.
     * Каждая транзакция в этом списке описывает отдельную финансовую операцию,
     * которая может включать доходы или расходы. Эти данные используются для отслеживания
     * и управления финансовой историей кошелька, предоставляя основу для
     * анализа моделей расходов, расчета балансов и наблюдения за тенденциями.
     */
    private List<Transaction> transactions;
    /**
     * Представляет собой набор категорий расходов или доходов в кошельке.
     * Каждая категория идентифицируется уникальным именем и хранит информацию,
     * например, тип категории и связанный с ней бюджет.
     * Эта карта используется для управления и организации финансовой деятельности по категориям,
     * позволяя отслеживать бюджет и управлять расходами.
     * Ключом в карте является имя категории, а значением — соответствующий
     * объект категории с подробной информацией о категории.
     */
    private Map<String, Category> categories;

    public Wallet() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.categories = new HashMap<>();
    }

    public Wallet(double balance) {
        this.balance = balance;
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
            Category newCategory = new Category(CategoryType.OTHER, categoryName,  budgetAmount);
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
