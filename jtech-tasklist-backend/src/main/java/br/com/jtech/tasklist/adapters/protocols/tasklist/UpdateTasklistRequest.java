package br.com.jtech.tasklist.adapters.protocols.tasklist;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request to update a tasklist")
public class UpdateTasklistRequest {
    
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Schema(description = "New title for the tasklist", example = "Updated Task List", required = true, maxLength = 255)
    private String title;
}
