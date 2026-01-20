package com.example.userservice;

public class User {

    private Long id;
    private String name;
    private String email;

    // Constructeur sans arguments (obligatoire pour Spring / JSON)
    public User() {
    }

    // Constructeur pratique
    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters et setters (pour accéder aux champs)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
