package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<User> getUserProfileInfo(Principal principal) {
        String userName = principal.getName();
        User user = userService.findByName(userName);
        return ResponseEntity.ok(user);
    }
}



