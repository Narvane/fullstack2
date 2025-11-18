package br.com.jtech.tasklist.config.usecases;

import br.com.jtech.tasklist.application.core.usecases.*;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.qualifiers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskUseCasesConfig {

    @Bean
    @CreateTask
    public TaskInputGateway createTaskUseCase(
            @CreateTask TaskOutputGateway outputGateway,
            TaskRepository repository,
            TasklistRepository tasklistRepository
    ) {
        return new CreateTaskUseCase(outputGateway, repository, tasklistRepository);
    }

    @Bean
    @UpdateTask
    public TaskInputGateway updateTaskUseCase(
            @UpdateTask TaskOutputGateway outputGateway,
            TaskRepository repository,
            TasklistRepository tasklistRepository
    ) {
        return new UpdateTaskUseCase(outputGateway, repository, tasklistRepository);
    }

    @Bean
    @DeleteTask
    public TaskInputGateway deleteTaskUseCase(
            TaskRepository repository,
            TasklistRepository tasklistRepository
    ) {
        return new DeleteTaskUseCase(repository, tasklistRepository);
    }

    @Bean
    @ListTasks
    public TaskInputGateway listTasksUseCase(
            @ListTasks TaskOutputGateway outputGateway,
            TaskRepository repository,
            TasklistRepository tasklistRepository
    ) {
        return new ListTasksUseCase(outputGateway, repository, tasklistRepository);
    }

    @Bean
    @CompleteTask
    public TaskInputGateway completeTaskUseCase(
            @CompleteTask TaskOutputGateway outputGateway,
            TaskRepository repository,
            TasklistRepository tasklistRepository
    ) {
        return new CompleteTaskUseCase(outputGateway, repository, tasklistRepository);
    }

}
