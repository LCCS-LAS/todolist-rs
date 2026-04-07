package com.example.todolist.Task.repository;

import com.example.todolist.Task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByIdUser(UUID idUser);
}
