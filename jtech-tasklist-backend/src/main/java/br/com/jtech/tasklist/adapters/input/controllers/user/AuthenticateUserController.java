package br.com.jtech.tasklist.adapters.input.controllers.user;

import br.com.jtech.tasklist.adapters.output.presenters.AuthenticateUserPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.AuthenticateUserResponse;
import br.com.jtech.tasklist.adapters.protocols.user.AuthenticateUserRequest;
import br.com.jtech.tasklist.application.ports.input.UserInputGateway;
import br.com.jtech.tasklist.application.ports.output.UserOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserInputData;
import br.com.jtech.tasklist.config.qualifiers.AuthenticateUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "User authentication and registration endpoints")
public class AuthenticateUserController {

    private final UserInputGateway inputGateway;
    private final AuthenticateUserPresenter presenter;

    public AuthenticateUserController(
            @AuthenticateUser UserInputGateway inputGateway,
            @AuthenticateUser UserOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (AuthenticateUserPresenter) outputGateway;
    }

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user with email and password, returns JWT token and user information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentication successful",
                    content = @Content(schema = @Schema(implementation = AuthenticateUserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content
            )
    })
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
