package com.example.todolist.Task.service;

import com.example.todolist.Task.entity.Task;
import com.example.todolist.Task.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Task save(Task task){
        return taskRepository.save(task);
    }

    public List<Task> findByIdUser(UUID user){
        return taskRepository.findByIdUser(user);
    }

    public Task update(Task task){
        return taskRepository.save(task);
    }

    public Optional<Task> findById(UUID id){
        return taskRepository.findById(id);
    }
}
