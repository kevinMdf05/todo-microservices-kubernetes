package com.example.taskservice;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final RestTemplate restTemplate;

    public TaskService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        tasks.add(new Task(1L, "Buy groceries", "Buy milk, bread and eggs", 1L, false));
        tasks.add(new Task(2L, "Finish report", "Complete the sales report for Q4", 2L, true));
        tasks.add(new Task(3L, "Plan trip", "Plan the summer vacation", 1L, false));
        tasks.add(new Task(4L, "Pay bills", "Pay electricity and internet bills", 3L, false));
        tasks.add(new Task(5L, "Clean house", "Do a full house cleaning", 2L, true));
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Task> getTasksByUserId(Long userId) {
        return tasks.stream()
                .filter(task -> task.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public TaskWithUserDto getTaskWithUserById(Long taskId) {
        Task task = getTaskById(taskId);
        if (task == null) {
            return null;
        }

        // Pour Docker: utilise le nom du service au lieu de localhost
        String userServiceUrl = System.getenv().getOrDefault("USER_SERVICE_URL", "http://localhost:8080");
        String url = userServiceUrl + "/users/" + task.getUserId();
        UserDto user = restTemplate.getForObject(url, UserDto.class);

        return new TaskWithUserDto(task, user);
    }
}
