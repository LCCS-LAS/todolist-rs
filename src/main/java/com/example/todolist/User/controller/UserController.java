package com.example.todolist.User.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.todolist.User.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todolist.User.entity.User;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> returnUser(@RequestBody User user){
        User userTest = userService.findByUsername(user.getUsername());
        if(userTest != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario Ja Cadastrado");
        }
        String pass = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(pass);

        User userCreated = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
