package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользовыатель с таким именем: " +username+ " не найден!!!") );
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        System.out.println("Сохранение пользователя: " + user.getUsername());
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println("Пароль закадирован: " + user.getPassword());
            userDao.addUser(user);
            System.out.println("Пользователь: " + user.getPassword() + " был добавлен в БД");
        } catch (Exception e) {
            System.out.println("Произошла ошибка при сохрании пользователя!! " + e.getMessage());
            throw e;
        }

    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User userChanged = userDao.getUserById(user.getId());
        if(!userChanged.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {

        userDao.removeUserById(id);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);


    }
}
