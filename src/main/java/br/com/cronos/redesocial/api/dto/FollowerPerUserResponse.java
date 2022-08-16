package br.com.cronos.redesocial.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class FollowerPerUserResponse {

    private Integer followerCount;
    private List<FollowerResponse> content;
}
