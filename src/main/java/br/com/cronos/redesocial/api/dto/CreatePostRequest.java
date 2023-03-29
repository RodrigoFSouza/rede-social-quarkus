package br.com.cronos.redesocial.api.dto;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePostRequest {
    @NotBlank
    @Schema(description = "Texto usado na publicação", example = "Novo post")
    private String text;
}
