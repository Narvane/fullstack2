package br.com.jtech.tasklist.adapters.output.repositories;

import br.com.jtech.tasklist.adapters.output.repositories.jpa.TasklistJpaRepository;
import br.com.jtech.tasklist.adapters.output.repositories.protocols.TasklistEntity;
import br.com.jtech.tasklist.application.core.domains.Tasklist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TasklistJpaAdapterTest {

    @Mock
    private TasklistJpaRepository repository;

    @InjectMocks
    private TasklistJpaAdapter adapter;

    private UUID tasklistId;
    private UUID userId;
    private TasklistEntity entity;
    private Tasklist domain;

    @BeforeEach
    void setUp() {
        tasklistId = UUID.randomUUID();
        userId = UUID.randomUUID();

        entity = TasklistEntity.builder()
                .id(tasklistId)
                .title("My Task List")
                .userId(userId)
                .build();

        domain = Tasklist.builder()
                .id(tasklistId)
                .title("My Task List")
                .userId(userId)
                .build();
    }

    @Test
    void shouldFindByIdSuccessfully() {
        when(repository.findById(tasklistId)).thenReturn(Optional.of(entity));

        Optional<Tasklist> result = adapter.findById(tasklistId);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(tasklistId);
        assertThat(result.get().getTitle()).isEqualTo("My Task List");
        assertThat(result.get().getUserId()).isEqualTo(userId);
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        when(repository.findById(tasklistId)).thenReturn(Optional.empty());

        Optional<Tasklist> result = adapter.findById(tasklistId);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldSaveTasklistSuccessfully() {
        when(repository.save(any(TasklistEntity.class))).thenReturn(entity);

        Tasklist result = adapter.save(domain);

        assertThat(result.getId()).isEqualTo(tasklistId);
        assertThat(result.getTitle()).isEqualTo("My Task List");
        assertThat(result.getUserId()).isEqualTo(userId);

        verify(repository).save(any(TasklistEntity.class));
    }

    @Test
    void shouldDeleteById() {
        adapter.deleteById(tasklistId);

        verify(repository).deleteById(tasklistId);
    }

    @Test
    void shouldFindAllTasklists() {
        List<TasklistEntity> entities = List.of(entity);
        when(repository.findAll()).thenReturn(entities);

        List<Tasklist> result = adapter.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(tasklistId);
        assertThat(result.get(0).getTitle()).isEqualTo("My Task List");
    }

    @Test
    void shouldFindByUserId() {
        List<TasklistEntity> entities = List.of(entity);
        when(repository.findByUserId(userId)).thenReturn(entities);

        List<Tasklist> result = adapter.findByUserId(userId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(userId);
    }

    @Test
    void shouldCheckExistsByUserIdAndTitle() {
        when(repository.existsByUserIdAndTitle(userId, "My Task List")).thenReturn(true);

        boolean result = adapter.existsByUserIdAndTitle(userId, "My Task List");

        assertThat(result).isTrue();
        verify(repository).existsByUserIdAndTitle(userId, "My Task List");
    }
}


