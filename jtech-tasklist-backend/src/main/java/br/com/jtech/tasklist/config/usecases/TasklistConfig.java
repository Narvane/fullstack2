package br.com.jtech.tasklist.config.usecases;

import br.com.jtech.tasklist.adapters.output.presenters.UpdateTasklistPresenter;
import br.com.jtech.tasklist.adapters.output.repositories.UpdateTasklistJpaAdapter;
import br.com.jtech.tasklist.adapters.output.repositories.jpa.TasklistJpaRepository;
import br.com.jtech.tasklist.application.core.usecases.UpdateTasklistUseCase;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.usecases.qualifiers.UpdateTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TasklistConfig {

    @Bean
    @UpdateTask
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UpdateTasklistPresenter updateTaskOutputGateway() {
        return new UpdateTasklistPresenter();
    }

    @Bean
    @UpdateTask
    public TasklistRepository updateTaskRepository(TasklistJpaRepository tasklistJpaRepository) {
        return new UpdateTasklistJpaAdapter(tasklistJpaRepository);
    }

    @Bean
    @UpdateTask
    public TasklistInputGateway updateTaskUseCase(
            @UpdateTask TasklistOutputGateway outputGateway,
            @UpdateTask TasklistRepository repository
    ) {
        return new UpdateTasklistUseCase(outputGateway, repository);
    }
}

