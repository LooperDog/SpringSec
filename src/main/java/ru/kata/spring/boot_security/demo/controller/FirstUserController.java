package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class FirstUserController {
    private final UserService userService;
    private final RoleService roleService;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    @Autowired
    public FirstUserController(UserService userService, RoleService roleService, DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration) {
        this.userService = userService;
        this.roleService = roleService;
        this.dataSourceTransactionManagerAutoConfiguration = dataSourceTransactionManagerAutoConfiguration;
    }

    @GetMapping("/create_first_user")
    public String showFirstUser() {
        if (!userService.listUsers().isEmpty()) {
            return "redirect:/login";
        }
        return "create_first_user";
    }

    @PostMapping("/create_first_user")
    public String createFirstUser(@RequestParam("username") String username,
                                  @RequestParam("lastName") String lastName,
                                  @RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  @RequestParam(value = "roles", required = false) String[] roles) {
        System.out.println("Получен ПОСТ-запрос на /create_first_user");
        System.out.println("Данные: \n" +
                "Имя " + username +
                "\nФамилия: " + lastName +
                "\nЕмаил: " + email +
                "\nПароль: " + password +
                "\nРоль: " + (roles != null ? java.util.Arrays.toString(roles) : "none"));

        if (!userService.listUsers().isEmpty()) {
            System.out.println("База не пуста, переход на /login");
            return "redirect:/login";
        }
        try {
            User user = new User();
            user.setUsername(username);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);

            if (roles != null && roles.length > 0) {
                System.out.println("Устанавливаем Роль: " + java.util.Arrays.toString(roles));
                user.setRoles(roleService.getSetOfRoles(roles));
            } else {
                System.out.println("Роль не выбрана!");
            }

            userService.addUser(user);
            System.out.println("Пользователь " + username + " успешно добавлен");
            return "redirect:/login";
        } catch (Exception e) {
            System.out.println("Что-то пошло не так, пользователь не создан" + e.getMessage());
            e.printStackTrace();
            return "redirect:/error";
        }


    }
}
