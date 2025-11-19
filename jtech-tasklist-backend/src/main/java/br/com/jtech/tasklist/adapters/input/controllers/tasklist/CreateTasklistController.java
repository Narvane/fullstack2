package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import br.com.jtech.tasklist.adapters.protocols.tasklist.CreateTasklistRequest;
import br.com.jtech.tasklist.adapters.output.presenters.CreateTasklistPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.CreateTasklistResponse;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.CreateTasklist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/api/v1/tasklists")
@Tag(name = "Tasklists", description = "Tasklist management endpoints")
public class CreateTasklistController {

    private final TasklistInputGateway inputGateway;
    private final CreateTasklistPresenter presenter;

    public CreateTasklistController(
            @CreateTasklist TasklistInputGateway inputGateway,
            @CreateTasklist TasklistOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (CreateTasklistPresenter) outputGateway;
    }

    @Operation(
            summary = "Create a new tasklist",
            description = "Creates a new tasklist for the authenticated user. Duplicate titles are not allowed per user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tasklist created successfully",
                    content = @Content(schema = @Schema(implementation = CreateTasklistResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - User not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - A tasklist with this title already exists",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<CreateTasklistResponse> create(@Valid @RequestBody CreateTasklistRequest request) {
        inputGateway.exec(
                TasklistInputData.builder()
                        .title(request.getTitle())
                        .build()
        );
        return ResponseEntity.status(CREATED).body(presenter.getResponse());
    }

}