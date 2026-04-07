package com.example.todolist.User.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID  id;

    @Column
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
