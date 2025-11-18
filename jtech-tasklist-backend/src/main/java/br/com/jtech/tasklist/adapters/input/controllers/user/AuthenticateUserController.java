package br.com.jtech.tasklist.adapters.input.controllers.user;

import br.com.jtech.tasklist.adapters.output.presenters.AuthenticateUserPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.AuthenticateUserResponse;
import br.com.jtech.tasklist.adapters.protocols.user.AuthenticateUserRequest;
import br.com.jtech.tasklist.application.ports.input.UserInputGateway;
import br.com.jtech.tasklist.application.ports.output.UserOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserInputData;
import br.com.jtech.tasklist.config.qualifiers.AuthenticateUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticateUserController {

    private final UserInputGateway inputGateway;
    private final AuthenticateUserPresenter presenter;

    public AuthenticateUserController(
            @AuthenticateUser UserInputGateway inputGateway,
            @AuthenticateUser UserOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (AuthenticateUserPresenter) outputGateway;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticateUserResponse> login(@Valid @RequestBody AuthenticateUserRequest request) {
        inputGateway.exec(
                UserInputData.builder()
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build()
        );
        return ResponseEntity.ok(presenter.getResponse());
    }
}
