package br.com.jtech.tasklist.adapters.input.controllers.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class CompleteTaskController {

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Void> complete(@PathVariable Long taskId) {
        // TODO: marcar como concluída (completed = true/false)
        return ResponseEntity.noContent().build();
    }
}
