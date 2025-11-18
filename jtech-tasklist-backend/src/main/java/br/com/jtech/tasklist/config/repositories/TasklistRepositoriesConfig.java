package br.com.jtech.tasklist.config.repositories;

import br.com.jtech.tasklist.adapters.output.repositories.TasklistJpaAdapter;
import br.com.jtech.tasklist.adapters.output.repositories.jpa.TasklistJpaRepository;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TasklistRepositoriesConfig {

    @Bean
    public TasklistRepository tasklistRepository(TasklistJpaRepository tasklistJpaRepository) {
        return new TasklistJpaAdapter(tasklistJpaRepository);
    }

}
