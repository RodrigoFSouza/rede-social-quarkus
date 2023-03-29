package br.com.cronos.redesocial.api.dto;

import br.com.cronos.redesocial.domain.model.Follower;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class FollowerResponse {
    @Schema(description = "Id do seguidor", example = "1")
    private Long id;

    @Schema(description = "Nome do seguidor", example = "Aparecido Silva")
    private String name;

    public FollowerResponse() {
    }

    public FollowerResponse(Follower follower) {
        this(follower.getId(), follower.getFollower().getName());
    }

    public FollowerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
