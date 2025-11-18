package br.com.jtech.tasklist.config.usecases;

import br.com.jtech.tasklist.application.core.usecases.CreateTasklistUseCase;
import br.com.jtech.tasklist.application.core.usecases.DeleteTasklistUseCase;
import br.com.jtech.tasklist.application.core.usecases.ListTasklistUseCase;
import br.com.jtech.tasklist.application.core.usecases.UpdateTasklistUseCase;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.qualifiers.CreateTasklist;
import br.com.jtech.tasklist.config.qualifiers.DeleteTaskList;
import br.com.jtech.tasklist.config.qualifiers.ListTasklist;
import br.com.jtech.tasklist.config.qualifiers.UpdateTasklist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TasklistUseCasesConfig {

    @Bean
    @UpdateTasklist
    public TasklistInputGateway updateTasklistUseCase(
            @UpdateTasklist TasklistOutputGateway outputGateway,
            TasklistRepository repository
    ) {
        return new UpdateTasklistUseCase(outputGateway, repository);
    }

    @Bean
    @CreateTasklist
    public TasklistInputGateway createTasklistUseCase(
            @CreateTasklist TasklistOutputGateway outputGateway,
            TasklistRepository repository
    ) {
        return new CreateTasklistUseCase(outputGateway, repository);
    }

    @Bean
    @DeleteTaskList
    public TasklistInputGateway deleteTasklistUseCase(
            TasklistRepository repository
    ) {
        return new DeleteTasklistUseCase(repository);
    }

    @Bean
    @ListTasklist
    public TasklistInputGateway listTasklistUseCase(
            @ListTasklist TasklistOutputGateway outputGateway,
            TasklistRepository repository
    ) {
        return new ListTasklistUseCase(outputGateway, repository);
    }

}
