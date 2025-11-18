package br.com.jtech.tasklist.config.repositories;

import br.com.jtech.tasklist.adapters.output.repositories.TaskJpaAdapter;
import br.com.jtech.tasklist.adapters.output.repositories.jpa.TaskJpaRepository;
import br.com.jtech.tasklist.adapters.output.repositories.jpa.TasklistJpaRepository;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskRepositoriesConfig {

    @Bean
    public TaskRepository taskRepository(TaskJpaRepository taskJpaRepository,
                                         TasklistJpaRepository tasklistJpaRepository) {
        return new TaskJpaAdapter(taskJpaRepository, tasklistJpaRepository);
    }

}
