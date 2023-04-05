package br.com.cronos.redesocial.api.dto;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateProfileRequest {
    @NotEmpty
    @Schema(description = "Nome do perfil", example = "JoãoSilva")
    private String name;
}
