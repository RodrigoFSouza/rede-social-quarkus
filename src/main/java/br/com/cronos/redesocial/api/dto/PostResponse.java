package br.com.cronos.redesocial.api.dto;

import br.com.cronos.redesocial.domain.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private String text;
    private LocalDateTime datetime;

    public static PostResponse fromDto(Post post) {
        PostResponse response = new PostResponse();
        response.setText(post.getText());
        response.setDatetime(post.getDateTime());

        return response;
    }
}
