package com.example.taskservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    // Injection de TaskService via le constructeur
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "task-service OK";
    }

    // GET /tasks -> toutes les tâches
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // GET /tasks/{id} -> une tâche par ID, ou 404 si non trouvée
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    // GET /tasks/user/{userId} -> tâches d'un utilisateur donné
    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUserId(@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }

    // GET /tasks/{id}/with-user -> tâche enrichie avec les infos de l'utilisateur
    @GetMapping("/{id}/with-user")
    public ResponseEntity<TaskWithUserDto> getTaskWithUser(@PathVariable Long id) {
        TaskWithUserDto result = taskService.getTaskWithUserById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
