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
class TaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String authToken;
    private String tasklistId;

    @BeforeEach
    void setUp() throws Exception {
        Map<String, String> registerRequest = new HashMap<>();
        registerRequest.put("name", "Test User");
        registerRequest.put("email", "task-test@example.com");
        registerRequest.put("password", "password123");

        String registerResponse = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> response = objectMapper.readValue(registerResponse, Map.class);
        authToken = (String) response.get("token");

        Map<String, String> tasklistRequest = new HashMap<>();
        tasklistRequest.put("title", "Test Tasklist");

        String tasklistResponse = mockMvc.perform(post("/api/v1/tasklists")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tasklistRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> tasklist = objectMapper.readValue(tasklistResponse, Map.class);
        tasklistId = (String) tasklist.get("id");
    }

    @Test
    void shouldCreateTaskSuccessfully() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("title", "My Task");
        request.put("tasklistId", tasklistId);

        mockMvc.perform(post("/api/v1/tasks")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("My Task"))
                .andExpect(jsonPath("$.tasklistId").value(tasklistId))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void shouldListTasks() throws Exception {
        Map<String, String> createRequest = new HashMap<>();
        createRequest.put("title", "List Test Task");
        createRequest.put("tasklistId", tasklistId);

        mockMvc.perform(post("/api/v1/tasks")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/tasks")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks[0].id").exists())
                .andExpect(jsonPath("$.tasks[0].title").value("List Test Task"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        Map<String, String> createRequest = new HashMap<>();
        createRequest.put("title", "Original Task");
        createRequest.put("tasklistId", tasklistId);

        String createResponse = mockMvc.perform(post("/api/v1/tasks")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> task = objectMapper.readValue(createResponse, Map.class);
        String taskId = (String) task.get("id");

        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("title", "Updated Task");
        updateRequest.put("completed", true);

        mockMvc.perform(put("/api/v1/tasks/" + taskId)
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void shouldCompleteTask() throws Exception {
        Map<String, String> createRequest = new HashMap<>();
        createRequest.put("title", "Task To Complete");
        createRequest.put("tasklistId", tasklistId);

        String createResponse = mockMvc.perform(post("/api/v1/tasks")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> task = objectMapper.readValue(createResponse, Map.class);
        String taskId = (String) task.get("id");

        mockMvc.perform(patch("/api/v1/tasks/" + taskId + "/complete")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        Map<String, String> createRequest = new HashMap<>();
        createRequest.put("title", "Task To Delete");
        createRequest.put("tasklistId", tasklistId);

        String createResponse = mockMvc.perform(post("/api/v1/tasks")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<String, Object> task = objectMapper.readValue(createResponse, Map.class);
        String taskId = (String) task.get("id");

        mockMvc.perform(delete("/api/v1/tasks/" + taskId)
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
}


