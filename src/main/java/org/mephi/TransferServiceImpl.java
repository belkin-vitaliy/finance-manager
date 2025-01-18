package org.mephi;

public class TransferServiceImpl implements TransferService {
    private AuthService authService;

    public TransferServiceImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void transferMoney(User sender, String recipientUsername, double amount) {
        if (authService.getUsers().containsKey(recipientUsername)) {
            User recipient = authService.getUsers().get(recipientUsername);
            if (sender.getWallet().getBalance() >= amount) {
                sender.getWallet().addTransaction(
                        new Transaction(-amount, new Category("Перевод"), "Перевод для " + recipientUsername)
                );
                recipient.getWallet().addTransaction(new Transaction(amount, new Category("Перевод"), "Получено от " + sender.getUsername()));
                System.out.printf("Успешный перевод %.2f от %s к %s.\n", amount, sender.getUsername(), recipientUsername);
            } else {
                System.out.println("Недостаточно средств для перевода.");
            }
        } else {
            System.out.println("Пользователь с таким именем не найден.");
        }
    }
}
