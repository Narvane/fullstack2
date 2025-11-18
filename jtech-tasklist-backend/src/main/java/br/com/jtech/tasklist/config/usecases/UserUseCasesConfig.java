package br.com.jtech.tasklist.config.usecases;

import br.com.jtech.tasklist.application.core.usecases.AuthenticateUserUseCase;
import br.com.jtech.tasklist.application.core.usecases.RegisterUserUseCase;
import br.com.jtech.tasklist.application.ports.input.UserInputGateway;
import br.com.jtech.tasklist.application.ports.output.UserOutputGateway;
import br.com.jtech.tasklist.application.ports.output.repositories.UserRepository;
import br.com.jtech.tasklist.config.infra.security.JwtService;
import br.com.jtech.tasklist.config.qualifiers.AuthenticateUser;
import br.com.jtech.tasklist.config.qualifiers.RegisterUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserUseCasesConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @RegisterUser
    public UserInputGateway registerUserUseCase(
            @RegisterUser UserOutputGateway outputGateway,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        return new RegisterUserUseCase(outputGateway, userRepository, passwordEncoder, jwtService);
    }

    @Bean
    @AuthenticateUser
    public UserInputGateway authenticateUserUseCase(
            @AuthenticateUser UserOutputGateway outputGateway,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        return new AuthenticateUserUseCase(outputGateway, userRepository, passwordEncoder, jwtService);
    }

}

