package br.com.cronos.redesocial.api.dto;

import br.com.cronos.redesocial.domain.model.Post;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    @Schema(description = "Texto da publicação", example = "Quarkus é performático")
    private String text;
    @Schema(description = "Data da publicação")
    private LocalDateTime datetime;

    public static PostResponse fromDto(Post post) {
        PostResponse response = new PostResponse();
        response.setText(post.getText());
        response.setDatetime(post.getDateTime());

        return response;
    }
}
