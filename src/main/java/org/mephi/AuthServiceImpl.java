package org.mephi;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class AuthServiceImpl implements AuthService {
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
            System.out.println("Такой пользователь уже существует.");
        } else {
            User newUser = new User(username, password);
            users.put(username, newUser);
            System.out.println("Регистрация прошла успешно.");
        }
    }

    @Override
    public User loginUser(String username, String password) {
        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            return users.get(username);
        } else {
            System.out.println("Неверный логин или пароль.");
            return null;
        }
    }

    @Override
    public Map<String, User> getUsers() {
        return users;
    }
}
