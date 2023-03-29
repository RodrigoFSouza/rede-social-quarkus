package br.com.cronos.redesocial.api.dto;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class CreateProfileRequest {
    @Schema(description = "Nome do perfil", example = "JoãoSilva")
    private String name;
}
