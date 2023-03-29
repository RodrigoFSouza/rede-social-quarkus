package br.com.cronos.redesocial.api.dto;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
public class FollowerPerUserResponse {

    @Schema(description = "Número de seguidores", example = "10")
    private Integer followerCount;

    private List<FollowerResponse> content;
}
