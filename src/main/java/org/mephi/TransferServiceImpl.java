package org.mephi;

public class TransferServiceImpl implements TransferService {
    public static final String TRANSFER_CATEGORY = "Перевод";
    public static final String INSUFFICIENT_FUNDS_MESSAGE = "Недостаточно средств для перевода.";
    public static final String USER_NOT_FOUND_MESSAGE = "Пользователь с таким именем не найден.";
    private final AuthService authService;
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
                        new Transaction(-amount, new Category(TRANSFER_CATEGORY), "Перевод для " + recipientUsername));
                transactionService.addTransaction(recipient.getWallet(),
                        new Transaction(amount, new Category(TRANSFER_CATEGORY), "Получено от " + sender.getUsername()));
                System.out.printf("Успешный перевод %.2f от %s к %s.\n", amount, sender.getUsername(), recipientUsername);
            } else {
                System.out.println(INSUFFICIENT_FUNDS_MESSAGE);
            }
        } else {
            System.out.println(USER_NOT_FOUND_MESSAGE);
        }
    }
}
