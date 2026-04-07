package com.example.todolist.User.service;

import com.example.todolist.User.entity.User;
import com.example.todolist.User.repository.UserRepostirory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepostirory userRepostirory;
    public UserService(UserRepostirory userRepostirory){
        this.userRepostirory = userRepostirory;
    }

    public User save(User user){
        return userRepostirory.save(user);
    }

    public User findByUsername(String nome){
        return userRepostirory.findByUsername(nome);
    }
}
