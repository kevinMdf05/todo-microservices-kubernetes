package com.example.userservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    public UserService() {
        // Données en mémoire (pas de base de données pour l'instant)
        users.add(new User(1L, "Alice", "alice@example.com"));
        users.add(new User(2L, "Bob", "bob@example.com"));
        users.add(new User(3L, "Charlie", "charlie@example.com"));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null; // On gérera le "pas trouvé" dans le controller plus tard
    }
}
