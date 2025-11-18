package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasklists")
public class DeleteTasklistController {

    @DeleteMapping("/{tasklistId}")
    public ResponseEntity<Void> delete(@PathVariable Long tasklistId) {
        // TODO: remover a tasklist (e suas tasks)
        return ResponseEntity.noContent().build();
    }
}
