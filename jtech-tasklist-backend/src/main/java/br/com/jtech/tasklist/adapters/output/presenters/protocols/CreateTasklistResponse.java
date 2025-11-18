package br.com.jtech.tasklist.adapters.output.presenters.protocols;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateTasklistResponse {
    private String id;
    private String title;
}
