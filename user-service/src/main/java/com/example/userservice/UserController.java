package com.example.userservice;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Injection du UserService via le constructeur
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "user-service OK";
    }

    // GET /users → liste de tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /users/{id} → un utilisateur par son id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            // Retourne HTTP 404 si l'utilisateur n'existe pas
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
