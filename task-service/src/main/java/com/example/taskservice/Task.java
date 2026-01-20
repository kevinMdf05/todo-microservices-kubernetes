package com.example.taskservice;

public class Task {

    private Long id;
    private String title;
    private String description;
    private Long userId;
    private boolean completed;

    // Constructeur vide (obligatoire pour la sérialisation JSON)
    public Task() {
    }

    // Constructeur pratique
    public Task(Long id, String title, String description, Long userId, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
