package br.com.jtech.tasklist.adapters.protocols.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Request to create a new task")
public class CreateTaskRequest {
    
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Schema(description = "Title of the task", example = "Complete project documentation", required = true, maxLength = 255)
    private String title;
    
    @Schema(description = "ID of the tasklist this task belongs to", example = "550e8400-e29b-41d4-a716-446655440000")
    private String tasklistId;
}

