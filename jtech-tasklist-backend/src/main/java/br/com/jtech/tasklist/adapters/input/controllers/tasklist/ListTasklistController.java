package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import br.com.jtech.tasklist.adapters.output.presenters.ListTasklistPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.ListTasklistResponse;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.ListTasklist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasklists")
@Tag(name = "Tasklists", description = "Tasklist management endpoints")
public class ListTasklistController {

    private final TasklistInputGateway inputGateway;
    private final ListTasklistPresenter presenter;

    public ListTasklistController(
            @ListTasklist TasklistInputGateway inputGateway,
            @ListTasklist TasklistOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (ListTasklistPresenter) outputGateway;
    }

    @Operation(
            summary = "List all tasklists",
            description = "Returns all tasklists belonging to the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tasklists retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ListTasklistResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - User not authenticated",
                    content = @Content
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<ListTasklistResponse> listTasklists() {
        inputGateway.exec(
                TasklistInputData.builder()
                        .build()
        );
        return ResponseEntity.ok(presenter.getResponse());
    }
}
