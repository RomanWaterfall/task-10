package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/user")
    public User getUser(Principal principal) {
        String userName = principal.getName();

        return userService.findByName(userName);
    }

    @GetMapping("i/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @PatchMapping("/users/update/{id}")
    public void updateUser(@PathVariable("id") int id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }
}






