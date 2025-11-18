package br.com.jtech.tasklist.adapters.input.controllers.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class UpdateTaskController {

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> update(
            @PathVariable Long taskId,
            @RequestBody Object request) {
        // TODO: atualizar título, descrição, taskListId (mover entre listas), etc.
        return ResponseEntity.noContent().build();
    }
}
