package org.mephi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Представляет финансовую категорию, которая связывает транзакции с определенным типом, именем,
 * и, при необходимости, ограничением бюджета. Категория используется для классификации финансовых
 * операций и управления ограничениями бюджета для распределения расходов или доходов.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /**
     * Указывает тип категории транзакции, связанной с текущей финансовой категорией.
     * Используется для определения назначения категории, например, для покупок,
     * переводов, вознаграждений, возвратов или других операций.
     */
    private CategoryType categoryType;
    /**
     * Представляет название финансовой категории.
     * Название используется как уникальный идентификатор категории,
     * позволяя классифицировать и бюджетировать финансовые транзакции.
     */
    private String name;
    /**
     * Представляет собой бюджетную сумму, выделенную для определенной финансовой категории.
     * Это значение служит пределом или ориентиром для управления расходами или доходами
     * в пределах категории, помогая обеспечить соблюдение финансовых целей и ограничений.
     */
    private double budget;

    public Category(CategoryType category) {
        this.categoryType = category;
    }

    public Category(String categoryName, CategoryType categoryType) {
        this.name = categoryName;
        this.categoryType = categoryType;
    }
}
