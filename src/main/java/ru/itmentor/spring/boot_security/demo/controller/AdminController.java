package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RequestMapping("/admin")
@RestController
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;

    }
    @ResponseBody
    @GetMapping("/user")
    public User getUser(Principal principal) {
        String userName = principal.getName();

        return userService.findByName(userName);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }


//@PostMapping("/users")
//public void createUser(@RequestBody User user) {
//    userService.saveUser(user);
//}
@PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        user.setName(user.getUsername());
        user.setPassword(user.getPassword());
        userService.saveUser(user);
        return ResponseEntity.ok("User created successfully");

    }








    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") int id, @RequestBody User updatedUser) {
        try {
            userService.updateUser(id, updatedUser);
            return ResponseEntity.ok("User updated successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
//    public void updateUser(@PathVariable("id") int id, @RequestBody User user) {
//        userService.updateUser(id, user);
//    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }
}






