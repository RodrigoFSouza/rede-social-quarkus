package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreatePostRequest;
import br.com.cronos.redesocial.domain.model.User;
import br.com.cronos.redesocial.domain.repository.UserRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {

    @Inject
    UserRepository userRepository;
    Long userId;

    @BeforeEach
    @Transactional
    void setUp() {
        var user = new User();
        user.setAge(30);
        user.setName("joao");
        userRepository.persist(user);
        userId = user.getId();
    }

    @Test
    @DisplayName("should create a post for user")
    void savePost() {
        var postRequest = new CreatePostRequest();
        postRequest.setText("New post");

        given()
            .contentType(ContentType.JSON)
            . body(postRequest)
            .pathParam("userId", userId)
        .when()
            .post()
        .then()
            .statusCode(201);
    }

    @Test
    @DisplayName("should return 404 trying to make a post for inexistent user")
    void savePostOfInexistentUser() {
        var postRequest = new CreatePostRequest();
        postRequest.setText("New post");

        var inexistentUserId = 999;

        given()
            .contentType(ContentType.JSON)
            . body(postRequest)
            .pathParam("userId", inexistentUserId)
        .when()
            .post()
        .then()
            .statusCode(404);
    }

    @Test
    void listPosts() {
    }
}