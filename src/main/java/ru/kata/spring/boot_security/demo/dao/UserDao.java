package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserDao {

    User getUserByEmail(String email);
    void addUser(User user);

    User getUserById(Long id);
    void updateUser(User user);

    void removeUserById(Long id);
    List<User> listUsers();
    Long countUsers();
}
