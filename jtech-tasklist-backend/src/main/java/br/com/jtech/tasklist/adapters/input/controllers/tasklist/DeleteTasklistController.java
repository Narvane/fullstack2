package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.input.data.TasklistInputData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasklists")
public class DeleteTasklistController {

    private final TasklistInputGateway inputGateway;

    public DeleteTasklistController(TasklistInputGateway inputGateway) {
        this.inputGateway = inputGateway;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        inputGateway.exec(
                TasklistInputData.builder()
                        .id(id)
                        .build()
        );
        return ResponseEntity.noContent().build();
    }
}
