package br.com.jtech.tasklist.adapters.protocols.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request to update a task")
public class UpdateTaskRequest {
    
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Schema(description = "New title for the task", example = "Updated task title", maxLength = 255)
    private String title;
    
    @Schema(description = "ID of the tasklist to move this task to", example = "550e8400-e29b-41d4-a716-446655440000")
    private String tasklistId;
    
    @Schema(description = "Completion status of the task", example = "true")
    private Boolean completed;
}

