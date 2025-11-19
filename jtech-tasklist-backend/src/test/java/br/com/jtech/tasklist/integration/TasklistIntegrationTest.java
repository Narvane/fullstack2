package br.com.jtech.tasklist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TasklistIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String authToken;
    private String userId;

    @BeforeEach
    void setUp() throws Exception {
        // Register and authenticate user
        Map<String, String> registerRequest = new HashMap<>();
        registerRequest.put("name", "Test User");
        registerRequest.put("email", "tasklist-test@example.com");
        registerRequest.put("password", "password123");

        String registerResponse = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract token from response (simplified - in real scenario use JSON parsing)
        Map<String, Object> response = objectMapper.readValue(registerResponse, Map.class);
        authToken = (String) response.get("token");
        userId = (String) response.get("id");
    }

    @Test
    void shouldCreateTasklistSuccessfully() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("title", "My Task List");

        mockMvc.perform(post("/api/v1/tasklists")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("My Task List"));
    }

    @Test
    void shouldListTasklists() throws Exception {
        // Create a tasklist first
        Map<String, String> createRequest = new HashMap<>();
        createRequest.put("title", "List Test Tasklist");

        mockMvc.perform(post("/api/v1/tasklists")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated());

        // List tasklists
        mockMvc.perform(get("/api/v1/tasklists")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tasklists").isArray())
                .andExpect(jsonPath("$.tasklists[0].id").exists())
                .andExpect(jsonPath("$.tasklists[0].title").value("List Test Tasklist"));
    }

    @Test
    void shouldUpdateTasklist() throws Exception {
        // Create a tasklist first
        Map<String, String> createRequest = new HashMap<>();
        createRequest.put("title", "Original Title");

        String createResponse = mockMvc.perform(post("/api/v1/tasklists")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> tasklist = objectMapper.readValue(createResponse, Map.class);
        String tasklistId = (String) tasklist.get("id");

        // Update tasklist
        Map<String, String> updateRequest = new HashMap<>();
        updateRequest.put("title", "Updated Title");

        mockMvc.perform(put("/api/v1/tasklists/" + tasklistId)
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void shouldDeleteTasklist() throws Exception {
        // Create a tasklist first
        Map<String, String> createRequest = new HashMap<>();
        createRequest.put("title", "To Be Deleted");

        String createResponse = mockMvc.perform(post("/api/v1/tasklists")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> tasklist = objectMapper.readValue(createResponse, Map.class);
        String tasklistId = (String) tasklist.get("id");

        // Delete tasklist
        mockMvc.perform(delete("/api/v1/tasklists/" + tasklistId)
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());

        // Verify it's deleted
        mockMvc.perform(get("/api/v1/tasklists")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tasklists").isEmpty());
    }

    @Test
    void shouldReturnForbiddenWhenNotAuthenticated() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("title", "My Task List");

        mockMvc.perform(post("/api/v1/tasklists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}


