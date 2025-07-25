package com.example.usermanagement.controller;

import com.example.usermanagement.model.User;
import com.example.usermanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService.clearUsers();
        userService.createUser(new User(null, "Alice", "alice@example.com"));
        userService.createUser(new User(null, "Bob", "bob@example.com"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Alice")))
                .andExpect(jsonPath("$[1].name", is("Bob")));
    }

    @Test
    public void testGetSingleUser() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alice")))
                .andExpect(jsonPath("$.email", is("alice@example.com")));
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("User not found")));
    }

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User(null, "Charlie", "charlie@example.com");
        
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Charlie")))
                .andExpect(jsonPath("$.email", is("charlie@example.com")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void testCreateUserInvalid() throws Exception {
        User invalidUser = new User(null, "Incomplete", null);
        
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User update = new User(null, "Alice Updated", null);
        
        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alice Updated")))
                .andExpect(jsonPath("$.email", is("alice@example.com")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("User deleted")));
        
        // Verify user is deleted
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteNonexistentUser() throws Exception {
        mockMvc.perform(delete("/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("User not found")));
    }
}