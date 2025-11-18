package br.com.jtech.tasklist.adapters.input.controllers.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticateUserController {

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Object request) {
        // TODO: autenticar e devolver token + dados básicos do usuário
        return ResponseEntity.ok().build();
    }
}
