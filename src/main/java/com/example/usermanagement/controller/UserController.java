package com.example.usermanagement.controller;

import com.example.usermanagement.model.User;
import com.example.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management API")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "List of users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User data"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> getUser(
            @Parameter(description = "ID of the user to retrieve") 
            @PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> updateUser(
            @Parameter(description = "ID of the user to update") 
            @PathVariable Long userId,
            @RequestBody User user) {
        Optional<User> updatedUser = userService.updateUser(userId, user);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "ID of the user to delete") 
            @PathVariable Long userId) {
        if (userService.deleteUser(userId)) {
            return ResponseEntity.ok(Map.of("message", "User deleted"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }
    }
}