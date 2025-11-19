package br.com.jtech.tasklist.adapters.input.controllers.user;

import br.com.jtech.tasklist.adapters.output.presenters.AuthenticateUserPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.AuthenticateUserResponse;
import br.com.jtech.tasklist.adapters.protocols.user.AuthenticateUserRequest;
import br.com.jtech.tasklist.application.ports.input.UserInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserInputData;
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
class AuthenticateUserControllerTest {

    @Mock
    private UserInputGateway inputGateway;

    @Mock
    private AuthenticateUserPresenter presenter;

    @InjectMocks
    private AuthenticateUserController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldAuthenticateUserSuccessfully() throws Exception {
        AuthenticateUserRequest request = new AuthenticateUserRequest();
        request.setEmail("john@example.com");
        request.setPassword("password123");

        AuthenticateUserResponse response = AuthenticateUserResponse.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .name("John Doe")
                .email("john@example.com")
                .token("jwt-token")
                .build();

        when(presenter.getResponse()).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.email").value(response.getEmail()))
                .andExpect(jsonPath("$.token").value(response.getToken()));

        verify(inputGateway, times(1)).exec(any(UserInputData.class));
        verify(presenter, times(1)).getResponse();
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsMissing() throws Exception {
        AuthenticateUserRequest request = new AuthenticateUserRequest();
        request.setPassword("password123");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(inputGateway, never()).exec(any());
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsMissing() throws Exception {
        AuthenticateUserRequest request = new AuthenticateUserRequest();
        request.setEmail("john@example.com");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(inputGateway, never()).exec(any());
    }
}

