package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.UserInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserInputData;
import br.com.jtech.tasklist.application.ports.output.UserOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.UserRepository;
import br.com.jtech.tasklist.config.infra.exceptions.UnauthorizedException;
import br.com.jtech.tasklist.config.infra.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticateUserUseCase implements UserInputGateway {

    private final UserOutputGateway outputGateway;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticateUserUseCase(
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
        var user = userRepository.findByEmail(data.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail());

        outputGateway.exec(
                UserOutputData.builder()
                        .id(user.getId().toString())
                        .name(user.getName())
                        .email(user.getEmail())
                        .token(token)
                        .build()
        );
    }

}

