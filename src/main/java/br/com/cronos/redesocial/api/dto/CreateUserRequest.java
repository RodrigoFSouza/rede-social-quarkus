package br.com.cronos.redesocial.api.dto;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserRequest {
    @NotBlank(message = "Name is required")
    @Schema(description = "Nome do usuário", example = "joao")
    private String name;
    @NotNull(message = "Age is required")
    @Schema(description = "Idade do usuario", example = "33")
    private Integer age;
}
