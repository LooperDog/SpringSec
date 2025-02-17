package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component

public class DbInitController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;

    public DbInitController(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }
    @Transactional
    @PostConstruct
    public void initDatabase() {
        if(userDao.countUsers() == 0) {
            Role adminRole = roleDao.getRoleByName("ROLE_ADMIN");
            if(adminRole != null) {
                roleDao.deleteRole(adminRole);
            }

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User admin = new User();
            admin.setUsername("admin");
            admin.setLastName("Adminov");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(roles);

            userDao.addUser(admin);
            System.out.println("Юзер был успешно создан");


        }
    }
}
