package com.example.todolist.Task.controller;

import com.example.todolist.Task.entity.Task;
import com.example.todolist.Task.service.TaskService;
import com.example.todolist.Task.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Task task, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        task.setIdUser((UUID) idUser);

        if (task.getStartAt() == null || task.getEndAt() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datas de início e fim são obrigatórias");
        }
        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data não pode estar no passado");
        }

        if(task.getStartAt().isAfter(task.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser menor que a de termino");
        }

        var taskSave = taskService.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(taskSave);
    }

    @GetMapping
    public List<Task> taskList(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = taskService.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Task task, HttpServletRequest request, @PathVariable UUID id){
      var taskId =  taskService.findById(id).orElse(null);

      if(task == null){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
      }

      var idUser = request.getAttribute("idUser");

      if (!task.getIdUser().equals(idUser)){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario não tem permissão para alterar essa tarefa");
      }
      Utils.copyNonNullProperties(task, taskId);
      var taskUpdate = taskService.save(taskId);
      return ResponseEntity.ok().body(taskUpdate);
    }
}
