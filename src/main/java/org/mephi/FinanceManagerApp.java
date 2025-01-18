package org.mephi;

import java.util.Scanner;

public class FinanceManagerApp {

    private static final AuthService authService = new AuthServiceImpl();
    private static final FileStorageService fileStorageService = new FileStorageServiceImpl();
    private static final TransferService transferService = new TransferServiceImpl(authService);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите команду: ");

            String command = scanner.nextLine();

            switch (command) {
                case "register":
                    System.out.print("Логин: ");
                    String username = scanner.nextLine();
                    System.out.print("Пароль: ");
                    String password = scanner.nextLine();
                    authService.registerUser(username, password);
                    break;
                case "login":
                    System.out.print("Логин: ");
                    username = scanner.nextLine();
                    System.out.print("Пароль: ");
                    password = scanner.nextLine();
                    User user = authService.loginUser(username, password);
                    if (user != null) {
                        userMenu(scanner, user);
                    }
                    break;
                case "exit":
                    System.exit(0);
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }

    private static void userMenu(Scanner scanner, User user) {
        Wallet wallet = user.getWallet();

        while (true) {
            System.out.printf("%s, введите команду: ", user.getUsername());
            String command = scanner.nextLine();

            switch (command) {
                case "add_income":
                    System.out.print("Сумма дохода: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    System.out.print("Категория: ");
                    String category = scanner.nextLine();
                    wallet.addTransaction(new Transaction(amount, new Category(category), "Доход"));
                    break;
                case "add_expense":
                    System.out.print("Сумма расхода: ");
                    amount = Double.parseDouble(scanner.nextLine());
                    System.out.print("Категория: ");
                    category = scanner.nextLine();
                    wallet.addTransaction(new Transaction(-amount, new Category(category), "Расход"));
                    break;
                case "set_budget":
                    System.out.print("Категория: ");
                    category = scanner.nextLine();
                    System.out.print("Бюджет: ");
                    double budget = Double.parseDouble(scanner.nextLine());
                    wallet.setBudgetForCategory(category, budget);
                    break;
                case "show_balance":
                    System.out.printf("Текущий баланс: %.2f\n", wallet.getBalance());
                    break;
                case "show_categories":
                    wallet.getCategories().forEach((name, cat) ->
                            System.out.printf("Категория: %s, Бюджет: %.2f\n", name, cat.getBudget())
                    );
                    break;
                case "check_budget":
                    System.out.print("Категория: ");
                    category = scanner.nextLine();
                    wallet.checkBudgetLimit(category);
                    break;
                case "save_data":
                    fileStorageService.saveWallet(wallet, "wallet_" + user.getUsername() + ".json");
                    break;
                case "load_data":
                    Wallet loadedWallet = fileStorageService.loadWallet("wallet_" + user.getUsername() + ".json");
                    if (loadedWallet != null) {
                        user.setWallet(loadedWallet);
                    }
                    break;
                case "transfer":
                    System.out.print("Получатель: ");
                    String recipientUsername = scanner.nextLine();
                    System.out.print("Сумма перевода: ");
                    amount = Double.parseDouble(scanner.nextLine());
                    transferService.transferMoney(user, recipientUsername, amount);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неизвестная команда.");
            }
        }
    }

}
