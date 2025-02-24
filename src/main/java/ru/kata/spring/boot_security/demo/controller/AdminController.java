package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
        return "redirect:/admin/users";
    }
    @GetMapping("users")
    public String pageUser(Model model){
        model.addAttribute("setUser", userService.listUsers());
        return "all_users";
    }

    @GetMapping(value = "/users/add")
    public String addUser(@ModelAttribute("user") User user, Model model){
        model.addAttribute("roles", roleService.getAllRoles());
        return "add";
    }

    @PostMapping(value = "/users/add")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] roles){
        user.setRoles(roleService.getSetOfRoles(roles));
             userService.addUser(user);
             return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PatchMapping("/users/{id}/edit")
    public String updateEdit(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] roles) {
        user.setRoles(roleService.getSetOfRoles(roles));
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id){
        userService.removeUserById(id);
        return "redirect:/admin/users";
    }
    @GetMapping("users/{id}")
    public String showAllUsers(@PathVariable("id") Long id, ModelMap modelMap){
        modelMap.addAttribute("user", userService.getUserById(id));
        return "show_users_by_id";
    }

}
