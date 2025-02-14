package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping
    public String homeAdmin(){
        return "redirect:/admin/user";
    }
    @GetMapping("users")
    public String pageUser(Model model){
        model.addAttribute("setUser", userService.listUsers());
        return "all_users";
    }

    @GetMapping(value = "/users/add")
    public String addUser(@ModelAttribute("users") User user, Model model){
        model.addAttribute("roles", roleService.getAllRoles());
        return "add";
    }

    @PostMapping(value = "/users/add")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] roles){
        user.setRoles(roleService.getSetOfRoles(roles));
             userService.addUser(user);
             return "redirect:/admin/user";
    }


}
