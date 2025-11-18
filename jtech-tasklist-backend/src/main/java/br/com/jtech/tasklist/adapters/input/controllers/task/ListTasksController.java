package br.com.jtech.tasklist.adapters.input.controllers.task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class ListTasksController {

    @GetMapping
    public ResponseEntity<Object> list(
            @RequestParam(name = "taskListId", required = false) Long taskListId) {
        // TODO: se taskListId != null, listar tasks dessa lista; senão, todas
        return ResponseEntity.ok().build();
    }
}
