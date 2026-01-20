package com.example.taskservice;

public class TaskWithUserDto {
    private Task task;
    private UserDto user;

    public TaskWithUserDto() {
    }

    public TaskWithUserDto(Task task, UserDto user) {
        this.task = task;
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
