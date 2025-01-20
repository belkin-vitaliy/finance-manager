package org.mephi;

import java.util.Map;

/**
 * Интерфейс AuthService отвечает за управление операциями аутентификации и регистрации пользователей.
 * Он предоставляет методы для регистрации пользователей, входа в систему и доступа к коллекции зарегистрированных пользователей.
 */
public interface AuthService {
    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param username имя пользователя, которое будет зарегистрировано
     * @param password пароль для регистрации пользователя
     */
    void registerUser(String username, String password);

    /**
     * Проверяет подлинность пользователя по указанному имени пользователя и паролю.
     * В случае успешной аутентификации возвращает соответствующий объект User.
     * В случае неудачной аутентификации возвращает значение null.
     *
     * @param username имя пользователя, пытающегося войти в систему
     * @param password пароль пользователя, пытающегося войти в систему
     * @return объект аутентифицированного пользователя, если вход в систему прошел успешно, или null, если аутентификация завершилась неудачей
     */
    User loginUser(String username, String password);

    /**
     * Получает список всех зарегистрированных пользователей системы.
     *
     * @return мапа, где ключом является имя пользователя, а значением — соответствующий объект User
     */
    Map<String, User> getUsers();

    /**
     * Checks whether the provided password matches the stored password for authentication.
     *
     * @param user             the user to authenticate
     * @param providedPassword the password provided during authentication
     * @return true if the provided password is correct, false otherwise
     */
    boolean checkPassword(User user, String providedPassword);

    /**
     * Находит и извлекает пользователя из системы по его имени пользователя.
     *
     * @param username имя пользователя, которого нужно найти
     * @return объект, представляющий пользователя, если он найден, или null, если пользователь не существует
     */
    Object findUserByUsername(String username);
}
