package br.com.jtech.tasklist.adapters.input.controllers.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class CreateTaskController {

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Object request) {
        // TODO: criar task (title, taskListId, etc.)
        return ResponseEntity.noContent().build();
    }
}
