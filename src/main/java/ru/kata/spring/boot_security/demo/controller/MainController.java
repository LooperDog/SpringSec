package ru.kata.spring.boot_security.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/")
    public String home(){
        return "Hello World";
    }

    @GetMapping("/authenticated")
    public String pageForUserAuthenticated(Principal principal){
        return "Page for user created" + principal.getName();
    }
}
