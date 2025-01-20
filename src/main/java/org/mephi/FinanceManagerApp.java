package org.mephi;

import java.util.Scanner;

public class FinanceManagerApp {

    public static final String INCOME_SUM_MESSAGE = "Сумма дохода: ";
    public static final String CATEGORY_LABEL = "Категория: ";
    public static final String USER_LOGIN_LABEL = "Логин: ";
    public static final String PASSWORD_LABEL = "Пароль: ";
    public static final String UNKNOWN_COMMAND_MESSAGE = "Неизвестная команда.";
    public static final String EXPENSE_SUM_MESSAGE = "Сумма расхода: ";
    public static final String BUDGET_LABEL = "Бюджет: ";
    public static final String RECIPIENT_LABEL = "Получатель: ";
    public static final String TRANSFER_AMOUNT_PROMPT = "Сумма перевода: ";
    public static final String BALANCE_FORMAT_STRING = "Текущий баланс: %.2f\n";
    public static final String CATEGORY_BUDGET_FORMAT = "Категория: %s, Бюджет: %.2f\n";
    public static final String COMMAND_REQUEST_FORMAT = "%s, введите команду: ";
    public static final String WALLET_PREFIX = "wallet_";
    public static final String JSON_EXTENSION = ".json";
    public static final String INCOME_CATEGORY = "Доход";
    public static final String EXPENSE_CATEGORY = "Расход";
    public static final String WELCOME_MESSAGE = "Зарегистрируйтесь (register)\n";
    public static final String LOGIN_PROMPT_MESSAGE = "Введите логин (login)\n";
    public static final String EXIT_PROMPT = "Выход (exit)";
    public static final String ENTER_COMMAND_MESSAGE = "Введите команду: ";

    public static final String COMMAND_REGISTER = "register";
    public static final String LOGIN_COMMAND = "login";
    public static final String COMMAND_EXIT = "exit";
    public static final String COMMAND_TRANSFER = "transfer";
    public static final String COMMAND_LOAD_DATA = "load_data";
    public static final String SAVE_DATA_ACTION = "save_data";
    public static final String COMMAND_CHECK_BUDGET = "check_budget";
    public static final String COMMAND_SHOW_CATEGORIES = "show_categories";
    public static final String COMMAND_SHOW_BALANCE = "show_balance";
    public static final String COMMAND_SET_BUDGET = "set_budget";
    public static final String COMMAND_ADD_EXPENSE = "add_expense";
    public static final String COMMAND_ADD_INCOME = "add_income";

    private static final AuthService authService = new AuthServiceImpl();
    private static final TransactionService transactionService = new TransactionServiceImpl();
    private static final FileStorageService fileStorageService = new FileStorageServiceImpl();
    private static final TransferService transferService = new TransferServiceImpl(authService, transactionService);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("" +
                    WELCOME_MESSAGE +
                    LOGIN_PROMPT_MESSAGE +
                    EXIT_PROMPT);
            System.out.print(ENTER_COMMAND_MESSAGE);

            String command = scanner.nextLine();
            String username;
            String password;

            switch (command) {
                case COMMAND_REGISTER:
                    System.out.print(USER_LOGIN_LABEL);
                    username = scanner.nextLine();
                    System.out.print(PASSWORD_LABEL);
                    password = scanner.nextLine();
                    authService.registerUser(username, password);
                    break;
                case LOGIN_COMMAND:
                    System.out.print(USER_LOGIN_LABEL);
                    username = scanner.nextLine();
                    System.out.print(PASSWORD_LABEL);
                    password = scanner.nextLine();
                    User user = authService.loginUser(username, password);
                    if (user != null) {
                        userMenu(scanner, user);
                    }
                    break;
                case COMMAND_EXIT:
                    System.exit(0);
                default:
                    System.out.println(UNKNOWN_COMMAND_MESSAGE);
            }
        }
    }

    private static void userMenu(Scanner scanner, User user) {
        Wallet wallet = user.getWallet();

        while (true) {
            System.out.printf(COMMAND_REQUEST_FORMAT, user.getUsername());
            String command = scanner.nextLine();
            double amount;
            String categoryName;
            Category category;

            switch (command) {
                case COMMAND_ADD_INCOME:
                    System.out.print(INCOME_SUM_MESSAGE);
                    amount = Double.parseDouble(scanner.nextLine());
                    System.out.print(CATEGORY_LABEL);
                    categoryName = scanner.nextLine();
                    category = new Category(categoryName, CategoryType.OTHER);
                    transactionService.addTransaction(wallet, new Transaction(amount, category, INCOME_CATEGORY));
                    break;
                case COMMAND_ADD_EXPENSE:
                    System.out.print(EXPENSE_SUM_MESSAGE);
                    amount = Double.parseDouble(scanner.nextLine());
                    System.out.print(CATEGORY_LABEL);
                    categoryName = scanner.nextLine();
                    category = new Category(categoryName, CategoryType.OTHER);
                    transactionService.addTransaction(wallet, new Transaction(-amount, category, EXPENSE_CATEGORY));
                    break;
                case COMMAND_SET_BUDGET:
                    System.out.print(CATEGORY_LABEL);
                    categoryName = scanner.nextLine();
                    System.out.print(BUDGET_LABEL);
                    double budget = Double.parseDouble(scanner.nextLine());
                    wallet.setBudgetForCategory(categoryName, budget);
                    break;
                case COMMAND_SHOW_BALANCE:
                    System.out.printf(BALANCE_FORMAT_STRING, wallet.getBalance());
                    break;
                case COMMAND_SHOW_CATEGORIES:
                    wallet.getCategories().forEach((name, cat) ->
                            System.out.printf(CATEGORY_BUDGET_FORMAT, name, cat.getCategoryType().name())
                    );
                    break;
                case COMMAND_CHECK_BUDGET:
                    System.out.print(CATEGORY_LABEL);
                    categoryName = scanner.nextLine();
                    wallet.checkBudgetLimit(categoryName);
                    break;
                case SAVE_DATA_ACTION:
                    fileStorageService.saveWallet(wallet, WALLET_PREFIX + user.getUsername() + JSON_EXTENSION);
                    break;
                case COMMAND_LOAD_DATA:
                    Wallet loadedWallet = fileStorageService.loadWallet(WALLET_PREFIX + user.getUsername() + JSON_EXTENSION);
                    if (loadedWallet != null) {
                        user.setWallet(loadedWallet);
                    }
                    break;
                case COMMAND_TRANSFER:
                    System.out.print(RECIPIENT_LABEL);
                    String recipientUsername = scanner.nextLine();
                    System.out.print(TRANSFER_AMOUNT_PROMPT);
                    amount = Double.parseDouble(scanner.nextLine());
                    transferService.transferMoney(user, recipientUsername, amount);
                    break;
                case COMMAND_EXIT:
                    return;
                default:
                    System.out.println(UNKNOWN_COMMAND_MESSAGE);
            }
        }
    }

}
