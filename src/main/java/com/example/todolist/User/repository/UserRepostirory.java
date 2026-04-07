package com.example.todolist.User.repository;

import com.example.todolist.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepostirory extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}
