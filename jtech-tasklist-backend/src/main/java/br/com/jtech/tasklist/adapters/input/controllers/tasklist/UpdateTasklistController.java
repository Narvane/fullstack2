package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasklists")
public class UpdateTasklistController {

    @PutMapping("/{tasklistId}")
    public ResponseEntity<Void> update(
            @PathVariable Long tasklistId,
            @RequestBody Object request) {
        // TODO: atualizar título da tasklist (e possivelmente outras infos)
        return ResponseEntity.noContent().build();
    }
}
