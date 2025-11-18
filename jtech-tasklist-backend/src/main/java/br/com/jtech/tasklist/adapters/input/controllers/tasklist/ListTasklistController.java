package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasklists")
public class ListTasklistController {

    @GetMapping
    public ResponseEntity<Object> listTasklists() {
        // TODO: listar todas as tasklists do usuário logado
        return ResponseEntity.ok().build();
    }
}
