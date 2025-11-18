package br.com.jtech.tasklist.config.repositories;

import br.com.jtech.tasklist.adapters.output.repositories.UserJpaAdapter;
import br.com.jtech.tasklist.adapters.output.repositories.jpa.UserJpaRepository;
import br.com.jtech.tasklist.application.ports.output.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRepositoriesConfig {

    @Bean
    public UserRepository userRepository(UserJpaRepository userJpaRepository) {
        return new UserJpaAdapter(userJpaRepository);
    }

}

