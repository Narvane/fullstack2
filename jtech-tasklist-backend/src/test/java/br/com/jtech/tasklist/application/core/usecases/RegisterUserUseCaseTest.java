package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.core.domains.User;
import br.com.jtech.tasklist.application.ports.output.UserOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserInputData;
import br.com.jtech.tasklist.application.ports.protocols.UserOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.UserRepository;
import br.com.jtech.tasklist.config.infra.exceptions.ConflictException;
import br.com.jtech.tasklist.config.infra.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock
    private UserOutputGateway outputGateway;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private RegisterUserUseCase useCase;

    private UserInputData inputData;
    private User savedUser;

    @BeforeEach
    void setUp() {
        inputData = UserInputData.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("password123")
                .build();

        UUID userId = UUID.randomUUID();
        savedUser = User.builder()
                .id(userId)
                .name("John Doe")
                .email("john@example.com")
                .password("encodedPassword")
                .build();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        when(userRepository.findByEmail(inputData.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(inputData.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(savedUser.getId(), savedUser.getEmail())).thenReturn("jwt-token");

        useCase.exec(inputData);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getName()).isEqualTo(inputData.getName());
        assertThat(capturedUser.getEmail()).isEqualTo(inputData.getEmail());
        assertThat(capturedUser.getPassword()).isEqualTo("encodedPassword");

        ArgumentCaptor<UserOutputData> outputCaptor = ArgumentCaptor.forClass(UserOutputData.class);
        verify(outputGateway).exec(outputCaptor.capture());
        UserOutputData outputData = outputCaptor.getValue();
        assertThat(outputData.getId()).isEqualTo(savedUser.getId().toString());
        assertThat(outputData.getName()).isEqualTo(savedUser.getName());
        assertThat(outputData.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(outputData.getToken()).isEqualTo("jwt-token");
    }

    @Test
    void shouldThrowConflictExceptionWhenUserAlreadyExists() {
        when(userRepository.findByEmail(inputData.getEmail())).thenReturn(Optional.of(savedUser));

        assertThatThrownBy(() -> useCase.exec(inputData))
                .isInstanceOf(ConflictException.class)
                .hasMessage("User with this email already exists");

        verify(userRepository, never()).save(any());
        verify(outputGateway, never()).exec(any());
    }
}

