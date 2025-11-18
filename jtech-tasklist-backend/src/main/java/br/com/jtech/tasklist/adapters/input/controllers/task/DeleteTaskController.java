package br.com.jtech.tasklist.adapters.input.controllers.task;

import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.config.qualifiers.DeleteTask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class DeleteTaskController {

    private final TaskInputGateway inputGateway;

    public DeleteTaskController(@DeleteTask TaskInputGateway inputGateway) {
        this.inputGateway = inputGateway;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        inputGateway.exec(
                TaskInputData.builder()
                        .id(id)
                        .build()
        );
        return ResponseEntity.noContent().build();
    }
}
