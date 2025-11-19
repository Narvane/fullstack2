package br.com.jtech.tasklist.adapters.input.controllers.user;

import br.com.jtech.tasklist.adapters.output.presenters.RegisterUserPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.RegisterUserResponse;
import br.com.jtech.tasklist.adapters.protocols.user.RegisterUserRequest;
import br.com.jtech.tasklist.application.ports.input.UserInputGateway;
import br.com.jtech.tasklist.application.ports.output.UserOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserInputData;
import br.com.jtech.tasklist.config.qualifiers.RegisterUser;
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

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "User authentication and registration endpoints")
public class RegisterUserController {

    private final UserInputGateway inputGateway;
    private final RegisterUserPresenter presenter;

    public RegisterUserController(
            @RegisterUser UserInputGateway inputGateway,
            @RegisterUser UserOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (RegisterUserPresenter) outputGateway;
    }

    @Operation(
            summary = "Register new user",
            description = "Creates a new user account and returns JWT token and user information"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = RegisterUserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User with this email already exists",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content
            )
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        inputGateway.exec(
                UserInputData.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build()
        );
        return ResponseEntity.status(CREATED).body(presenter.getResponse());
    }
}
