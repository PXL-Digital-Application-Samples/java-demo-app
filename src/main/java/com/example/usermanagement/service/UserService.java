package com.example.usermanagement.service;

import com.example.usermanagement.model.User;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @PostConstruct
    public void seedUsers() {
        createUser(new User(null, "Alice", "alice@example.com"));
        createUser(new User(null, "Bob", "bob@example.com"));
        createUser(new User(null, "Charlie", "charlie@example.com"));
    }

    public User createUser(User user) {
        Long id = nextId.getAndIncrement();
        user.setId(id);
        users.put(id, user);
        return user;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        User existingUser = users.get(id);
        if (existingUser == null) {
            return Optional.empty();
        }
        
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        
        return Optional.of(existingUser);
    }

    public boolean deleteUser(Long id) {
        return users.remove(id) != null;
    }

    // For testing purposes
    public void clearUsers() {
        users.clear();
        nextId.set(1);
    }

    public void resetToDefaults() {
        clearUsers();
        seedUsers();
    }
}