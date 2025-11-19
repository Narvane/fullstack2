package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.core.domains.User;
import br.com.jtech.tasklist.application.ports.input.UserInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserInputData;
import br.com.jtech.tasklist.application.ports.output.UserOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.UserRepository;
import br.com.jtech.tasklist.config.infra.exceptions.ConflictException;
import br.com.jtech.tasklist.config.infra.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class RegisterUserUseCase implements UserInputGateway {

    private final UserOutputGateway outputGateway;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterUserUseCase(
            UserOutputGateway outputGateway,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.outputGateway = outputGateway;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public void exec(UserInputData data) {
        if (userRepository.findByEmail(data.getEmail()).isPresent()) {
            throw new ConflictException("User with this email already exists");
        }

        var user = User.builder()
                .name(data.getName())
                .email(data.getEmail())
                .password(passwordEncoder.encode(data.getPassword()))
                .build();

        var savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getId(), savedUser.getEmail());

        outputGateway.exec(
                UserOutputData.builder()
                        .id(savedUser.getId().toString())
                        .name(savedUser.getName())
                        .email(savedUser.getEmail())
                        .token(token)
                        .build()
        );
    }

}

