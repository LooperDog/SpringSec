package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class MyController {

    private final UserService userService;

    public MyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        if(userService.listUsers().isEmpty()) {
            return "create_first_user";
        }
        return "redirect:/admin";
    }
}
