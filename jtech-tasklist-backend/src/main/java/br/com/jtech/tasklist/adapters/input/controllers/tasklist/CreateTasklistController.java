package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import br.com.jtech.tasklist.adapters.input.controllers.protocols.tasklist.CreateTasklistRequest;
import br.com.jtech.tasklist.adapters.input.controllers.protocols.tasklist.CreateTasklistResponse;
import br.com.jtech.tasklist.application.ports.input.CreateTasklistInputGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/api/v1/tasklists")
@RequiredArgsConstructor
public class CreateTasklistController {

    private final CreateTasklistInputGateway inputGateway;

    @PostMapping
    public ResponseEntity<CreateTasklistResponse> create(@RequestBody CreateTasklistRequest request) {
        var createdTask = inputGateway.exec(request.getTitle());
        return ResponseEntity.status(CREATED).body(
                CreateTasklistResponse.builder()
                        .id(createdTask.getId().toString())
                        .build()
        );
    }

}