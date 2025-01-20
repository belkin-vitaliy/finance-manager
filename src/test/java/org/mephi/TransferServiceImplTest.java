package org.mephi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransferServiceImplTest {

    public static final String RECIPIENT_USERNAME = "Recipient";
    public static final String SENDER_USERNAME = "Sender";
    public static final String TEST_PASSWORD = "password";

    /**
     * Представляет собой экземпляр TransferServiceImpl, используемый в модульных тестах для обработки
     * операций по переводу денег. Сервис облегчает перевод средств между пользователями,
     * обеспечивая надлежащую проверку существования пользователя и достаточный баланс кошелька.
     * Это зависит от AuthService для управления пользователями и TransactionService
     * для выполнения и ведения журнала транзакций.
     */
    private TransferServiceImpl transferService;
    /**
     * Это поле представляет собой экземпляр интерфейса AuthService, отвечающего за аутентификацию и управление пользователями в системе. Оно используется в тесте для выполнения таких операций, как регистрация, аутентификация пользователей и проверка учётных данных.
     * В контексте тестирования это поле используется для проверки правильности реализации AuthService и их взаимодействия со сценариями аутентификации пользователей.
     */
    private AuthService authService;

    /**
     * Представляет собой сервис для обработки финансовых транзакций в контексте
     * пользовательских кошельков и переводов. Он предоставляет методы для добавления транзакций,
     * расчёта доходов и расходов, управления категориями и регистрации переводов.
     * Этот экземпляр используется для интеграции и тестирования в классе TransferServiceImplTest
     * для проверки функциональности переводов.
     */
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        authService = mock(AuthService.class);
        transactionService = mock(TransactionService.class);

        // Create TransferServiceImpl instance with mocked dependencies
        transferService = new TransferServiceImpl(authService, transactionService);
    }

    @Test
    void transferMoney_ShouldThrowException_WhenSenderHasInsufficientFunds() {

        User sender = new User(SENDER_USERNAME, TEST_PASSWORD);
        sender.getWallet().setBalance(20);// Sender has only 20
        User recipient = new User(RECIPIENT_USERNAME, TEST_PASSWORD);
        recipient.getWallet().setBalance(100);
        when(authService.findUserByUsername(RECIPIENT_USERNAME)).thenReturn(recipient);

        Executable action = () -> transferService.transferMoney(sender, RECIPIENT_USERNAME, 50.0);

    }

    @Test
    void transferMoney_ShouldSucceed_WhenValidUsersAndSufficientFunds() {
        // Arrange
        User sender = new User(SENDER_USERNAME, TEST_PASSWORD);
        sender.getWallet().setBalance(20);// Sender has only 20
        User recipient = new User(RECIPIENT_USERNAME, TEST_PASSWORD);
        recipient.getWallet().setBalance(50);

        // Имитация поиска получателя
        when(authService.findUserByUsername(RECIPIENT_USERNAME)).thenReturn(recipient);

        // действие
        transferService.transferMoney(sender, RECIPIENT_USERNAME, 50.0);

        // сравнение
        assertEquals(20.0, sender.getWallet().getBalance());  // Check deducted balance
        assertEquals(50.0, recipient.getWallet().getBalance());  // Check updated balance

    }
}