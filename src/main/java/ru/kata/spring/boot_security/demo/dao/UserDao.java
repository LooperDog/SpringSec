package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDao  {

    User getUserByEmail(String email);
    void addUser(User user);

    User getUserById(Long id);
    void updateUser(User user);

    void removeUserById(Long id);
    List<User> listUsers();
    Optional<User> findByUsername(String name);
}
