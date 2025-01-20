package org.mephi;

public class TransferServiceImpl implements TransferService {

    public static final String INSUFFICIENT_FUNDS_MESSAGE = "Недостаточно средств для перевода.";
    public static final String USER_NOT_FOUND_MESSAGE = "Пользователь с таким именем не найден.";
    public static final String TRANSFER_LABEL = "Перевод для ";
    public static final String TRANSFER_RECEIVED_FROM = "Получено от ";
    public static final String SUCCESSFUL_TRANSFER_TEMPLATE = "Успешный перевод %.2f от %s к %s.\n";

    /**
     * Представляет собой сервис, отвечающий за обработку аутентификации и операций, связанных с пользователями.
     * Обеспечивает функциональность для регистрации пользователей, входа в систему, получения информации о зарегистрированных пользователях,
     * а также проверку учётных данных пользователей. Этот экземпляр используется для упрощения взаимодействия с пользователями,     * аутентификации и обеспечения контроля доступа в приложении.
     */
    private final AuthService authService;

    /**
     * Сервис для управления финансовыми транзакциями пользователей.
     * Используется для добавления транзакций в кошелек, обновления баланса,
     * а также выполнения операций, связанных с категориями и бюджетами.
     * Инкапсулирует логику обработки финансовых операций для обеспечения их корректности
     * и соблюдения ограничений, таких как лимиты по бюджету.
     */
    private final TransactionService transactionService;

    public TransferServiceImpl(AuthService authService, TransactionService transactionService) {
        this.authService = authService;
        this.transactionService = transactionService;
    }

    @Override
    public void transferMoney(User sender, String recipientUsername, double amount) {
        if (authService.getUsers().containsKey(recipientUsername)) {
            User recipient = authService.getUsers().get(recipientUsername);
            if (sender.getWallet().getBalance() >= amount) {
                transactionService.addTransaction(sender.getWallet(),
                        new Transaction(-amount, new Category(CategoryType.TRANSFER), TRANSFER_LABEL + recipientUsername));
                transactionService.addTransaction(recipient.getWallet(),
                        new Transaction(amount, new Category(CategoryType.TRANSFER), TRANSFER_RECEIVED_FROM + sender.getUsername()));
                System.out.printf(SUCCESSFUL_TRANSFER_TEMPLATE, amount, sender.getUsername(), recipientUsername);
            } else {
                System.out.println(INSUFFICIENT_FUNDS_MESSAGE);
            }
        } else {
            System.out.println(USER_NOT_FOUND_MESSAGE);
        }
    }
}
