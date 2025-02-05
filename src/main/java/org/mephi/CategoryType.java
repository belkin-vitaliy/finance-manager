package org.mephi;

public enum CategoryType {
    /**
     * Представляет собой категорию для денежных переводов. Эта категория обычно используется
     * для классификации транзакций, при которых деньги перемещаются с одного объекта, аккаунта
     * или кошелька на другой без привязки к какой-либо конкретной покупке или вознаграждению.
     */
    TRANSFER,

    /**
     * Представляет собой категорию для приобретения товаров или услуг.
     * Обычно используется для классификации операций, связанных с расходами,
     * понесенными в связи с приобретением товаров или использованием услуг.
     */
    PURCHASE,

    /**
     * Представляет категорию, связанную с вознаграждениями или бонусами.
     * Обычно используется для классификации транзакций, связанных с поощрениями,
     * такими как кэшбэк, подарки или другие формы финансовых выгод.
     */
    REWARD,

    /**
     * Представляет категорию для операций по возврату средств.
     * Обычно используется для классификации операций, при которых средства возвращаются
     * пользователю за ранее совершенные платежи или покупки.
     */
    REFUND,

    /**
     * Представляет собой категорию для транзакций, которые не подпадают ни под одну из конкретных заранее заданных категорий.
     * Используется в качестве категории по умолчанию для неклассифицированных или прочих финансовых операций.
     */
    OTHER
}