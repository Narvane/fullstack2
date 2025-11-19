package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.core.domains.Tasklist;
import br.com.jtech.tasklist.application.core.domains.User;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.application.ports.protocols.TasklistOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.infra.exceptions.ConflictException;
import br.com.jtech.tasklist.config.infra.exceptions.UnauthorizedException;
import br.com.jtech.tasklist.config.infra.security.SecurityContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTasklistUseCaseTest {

    @Mock
    private TasklistOutputGateway outputGateway;

    @Mock
    private TasklistRepository tasklistRepository;

    @InjectMocks
    private CreateTasklistUseCase useCase;

    private UUID userId;
    private TasklistInputData inputData;
    private Tasklist savedTasklist;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        User user = User.builder().id(userId).build();
        SecurityContext.setCurrentUser(user);

        inputData = TasklistInputData.builder()
                .title("My Task List")
                .build();

        savedTasklist = Tasklist.builder()
                .id(UUID.randomUUID())
                .title("My Task List")
                .userId(userId)
                .build();
    }

    @AfterEach
    void tearDown() {
        SecurityContext.clear();
    }

    @Test
    void shouldCreateTasklistSuccessfully() {
        when(tasklistRepository.existsByUserIdAndTitle(userId, inputData.getTitle())).thenReturn(false);
        when(tasklistRepository.save(any(Tasklist.class))).thenReturn(savedTasklist);

        useCase.exec(inputData);

        ArgumentCaptor<Tasklist> tasklistCaptor = ArgumentCaptor.forClass(Tasklist.class);
        verify(tasklistRepository).save(tasklistCaptor.capture());
        Tasklist capturedTasklist = tasklistCaptor.getValue();
        assertThat(capturedTasklist.getTitle()).isEqualTo(inputData.getTitle());
        assertThat(capturedTasklist.getUserId()).isEqualTo(userId);

        ArgumentCaptor<TasklistOutputData> outputCaptor = ArgumentCaptor.forClass(TasklistOutputData.class);
        verify(outputGateway).exec(outputCaptor.capture());
        TasklistOutputData outputData = outputCaptor.getValue();
        assertThat(outputData.getId()).isEqualTo(savedTasklist.getId().toString());
        assertThat(outputData.getTitle()).isEqualTo(savedTasklist.getTitle());
    }

    @Test
    void shouldThrowUnauthorizedExceptionWhenUserNotAuthenticated() {
        SecurityContext.clear();

        assertThatThrownBy(() -> useCase.exec(inputData))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessage("User not authenticated");

        verify(tasklistRepository, never()).save(any());
        verify(outputGateway, never()).exec(any());
    }

    @Test
    void shouldThrowConflictExceptionWhenTitleAlreadyExists() {
        when(tasklistRepository.existsByUserIdAndTitle(userId, inputData.getTitle())).thenReturn(true);

        assertThatThrownBy(() -> useCase.exec(inputData))
                .isInstanceOf(ConflictException.class)
                .hasMessage("A tasklist with this title already exists");

        verify(tasklistRepository, never()).save(any());
        verify(outputGateway, never()).exec(any());
    }
}

