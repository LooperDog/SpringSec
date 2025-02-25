
package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class DbInitController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public DbInitController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    @PostConstruct
    public void initDatabase() {

        Role adminRole = roleService.getRoleByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Role("ROLE_ADMIN");
            roleService.addRole(adminRole);
            System.out.println("Роль ADMIN создана!");
        }

        Role userRole = roleService.getRoleByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleService.addRole(userRole);
            System.out.println("Роль USER создана!");
        }

        try {
            userService.findByUsername("admin");
            System.out.println("Пользователь ADMIN уже существует");
        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException e) {

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User admin = new User();
            admin.setUsername("admin");
            admin.setLastName("Adminov");
            admin.setEmail("admin@example.com");
            admin.setPassword("admin");
            admin.setRoles(roles);

            userService.addUser(admin);
            System.out.println("Юзер был успешно создан");
        }
    }
}
