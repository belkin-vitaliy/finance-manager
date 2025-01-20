package org.mephi;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
public class AuthServiceImpl implements AuthService {
    public static final String USER_NOT_NULL_MESSAGE = "Пользователь не может быть пустым";
    public static final String ERROR_EMPTY_PASSWORD = "Пароль не может быть пустым";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "Такой пользователь уже существует.";
    public static final String AUTHENTICATION_ERROR_MESSAGE = "Неверный логин или пароль.";
    public static final String REGISTRATION_SUCCESS_MESSAGE = "Регистрация прошла успешно.";

    private final Map<String, User> users;

    public AuthServiceImpl(Map<String, User> users) {
        this.users = users;
    }

    public AuthServiceImpl() {
        this.users = new HashMap<>();
    }

    @Override
    public void registerUser(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println(USER_ALREADY_EXISTS_MESSAGE);
        } else {
            User newUser = new User(username, password);
            users.put(username, newUser);
            System.out.println(REGISTRATION_SUCCESS_MESSAGE);
        }
    }

    @Override
    public User loginUser(String username, String password) {
        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            return users.get(username);
        } else {
            System.out.println(AUTHENTICATION_ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public Map<String, User> getUsers() {
        return users;
    }

    @Override
    public boolean checkPassword(User user, String providedPassword) {
        if (user == null) {
            throw new IllegalArgumentException(USER_NOT_NULL_MESSAGE);
        }

        if (providedPassword == null) {
            throw new IllegalArgumentException(ERROR_EMPTY_PASSWORD);
        }

        String storedPassword = user.getPassword(); // Предполагается, что пароль сохранен в виде обычного текста или хэширован
        return Objects.equals(storedPassword, providedPassword);
    }

    @Override
    public Object findUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException(USER_NOT_NULL_MESSAGE);
        }
        //TODO реализовать поиск пользователя в базе
        return null;
    }
}
