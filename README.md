# Finance Manager

## Описание проекта

Проект **Finance Manager** — это приложение для управления финансами, включающее функции перевода денег между пользователями, мониторинга финансовых транзакций и управления пользовательскими кошельками. Основной целью данного приложения является предоставление удобного, безопасного и эффективного способа контроля финансов.

### Основные функции

- Регистрация и аутентификация пользователей через `AuthService`.
- Управление переводами между пользователями с проверкой:
    - Наличия отправителя и получателя.
    - Достаточного баланса у отправителя.
- Логирование всех финансовых операций через `TransactionService`.
- Удобный интерфейс для взаимодействия с пользователями и сервисами.
- Управление доходами и расходами
- Работа с кошельком пользователя
- Вывод информации
- Оповещения пользователя
- Сохранение и загрузка данных
- Чтение команд в цикле
- Валидация данных

---

## Структура проекта

Основные компоненты проекта:

1. **Сервисы**
    - `TransferServiceImpl` — обработка переводов.
    - `AuthService` — управление пользователями.
    - `TransactionService` — работа с журналом операций.

2. **Модели**
    - `User` — пользователь системы, содержащий данные о профиле и кошельке.
    - `Wallet` — информация о состоянии баланса пользователя.

3. **Тесты**
    - Покрытие функциональности через модульные тесты с использованием JUnit5 и Mockito.

---

## Установка и настройка

### Требования

- OpenJDK версии 23 или выше.
- Maven для управления зависимостями.
- IntelliJ IDEA 2024.3 (или другой IDE).

### Сборка проекта

1. Склонируйте репозиторий:
   ```bash
   git clone <ссылка на репозиторий>
   cd finance-manager
   ```

2. Соберите проект:
   ```bash
   mvn clean install
   ```

3. Убедитесь, что все тесты проходят:
   ```bash
   mvn test
   ```

---

## Тестирование

Тесты находятся в папке `src/test/java/org/mephi`.

Для запуска написан JUnit5, примеры тестирования:
- Проверка корректной работы переводов (`TransferServiceImplTest`).
- Мокирование зависимостей с использованием `Mockito`.

Пример команды для запуска тестов:

```bash
mvn test
```

---

## Используемые технологии

- **Java 23** - основной язык разработки.
- **Maven** - сборка и управление зависимостями.
- **JUnit 5** - тестирование.
- **Mockito** - для создания мок-объектов.

---

## Пример использования

### Перевод средств между пользователями

#### Код для вызова перевода:
```java
User sender = new User("SenderName", "password123");
User recipient = new User("RecipientName", "password123");

// Установка балансов
sender.getWallet().setBalance(500.0);
recipient.getWallet().setBalance(100.0);

// Инициализация сервиса
TransferServiceImpl transferService = new TransferServiceImpl(authService, transactionService);

// Перевод средств
transferService.transferMoney(sender, recipient.getUsername(), 200.0);

// Проверка результата
System.out.println("Остаток баланса отправителя: " + sender.getWallet().getBalance());
System.out.println("Баланс получателя: " + recipient.getWallet().getBalance());
```

Ожидаемый результат:
- Баланс отправителя уменьшен на 200.0.
- Баланс получателя увеличен на 200.0.

---

## Авторы

Проект был разработан для внутренних нужд и образовательных целей.

---

## Лицензия

Этот проект использует свободную лицензию. Подробности можно описать, если ваш проект открытый.# finance-manager