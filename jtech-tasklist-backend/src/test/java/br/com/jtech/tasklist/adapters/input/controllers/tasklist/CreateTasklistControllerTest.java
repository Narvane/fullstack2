package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import br.com.jtech.tasklist.adapters.output.presenters.CreateTasklistPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.CreateTasklistResponse;
import br.com.jtech.tasklist.adapters.protocols.tasklist.CreateTasklistRequest;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CreateTasklistControllerTest {

    @Mock
    private TasklistInputGateway inputGateway;

    @Mock
    private CreateTasklistPresenter presenter;

    @InjectMocks
    private CreateTasklistController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldCreateTasklistSuccessfully() throws Exception {
        CreateTasklistRequest request = new CreateTasklistRequest();
        request.setTitle("My Task List");

        CreateTasklistResponse response = CreateTasklistResponse.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .title("My Task List")
                .build();

        when(presenter.getResponse()).thenReturn(response);

        mockMvc.perform(post("/api/v1/tasklists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.title").value(response.getTitle()));

        verify(inputGateway, times(1)).exec(any(TasklistInputData.class));
        verify(presenter, times(1)).getResponse();
    }

    @Test
    void shouldReturnBadRequestWhenTitleIsMissing() throws Exception {
        CreateTasklistRequest request = new CreateTasklistRequest();

        mockMvc.perform(post("/api/v1/tasklists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(inputGateway, never()).exec(any());
    }
}

