package br.com.jtech.tasklist.application.core.domains;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private UUID id;
    private String title;
    private boolean completed;

}
