package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreatePostRequest;
import br.com.cronos.redesocial.domain.model.Follower;
import br.com.cronos.redesocial.domain.model.Post;
import br.com.cronos.redesocial.domain.model.User;
import br.com.cronos.redesocial.domain.repository.FollowerRepository;
import br.com.cronos.redesocial.domain.repository.PostRepository;
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
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {

    @Inject
    UserRepository userRepository;

    @Inject
    FollowerRepository followerRepository;
    @Inject
    PostRepository postRepository;
    Long userId;
    Long userNotFollowerId;
    Long userFollowerId;

    @BeforeEach
    @Transactional
    void setUp() {
        var user = new User();
        user.setAge(30);
        user.setName("joao");
        userRepository.persist(user);
        userId = user.getId();

        var post = new Post();
        post.setText("New post");
        post.setUser(user);
        postRepository.persist(post);

        var userNotFollower = new User();
        userNotFollower.setAge(33);
        userNotFollower.setName("pedro");
        userRepository.persist(userNotFollower);
        userNotFollowerId = userNotFollower.getId();

        var userFollower = new User();
        userFollower.setAge(28);
        userFollower.setName("maria");
        userRepository.persist(userFollower);
        userFollowerId = userFollower.getId();

        Follower follower = new Follower();
        follower.setUser(user);
        follower.setFollower(userFollower);

        followerRepository.persist(follower);
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
    @DisplayName("should return 404 when user doesn't exist")
    void listPostUserNotFoundTest() {
        var inexistentUserId = 999;

        given()
            .pathParam("userId", inexistentUserId)
        .when()
            .get()
            .then()
        .statusCode(404);
    }

    @Test
    @DisplayName("should return 400 when followerId header is not present")
    void listPostFollowerHeaderNotSendTest() {
        given()
            .pathParam("userId", userId)
        .when()
            .get()
        .then()
            .statusCode(400)
            .body(is("You forgot the header followerId"));
    }

    @Test
    @DisplayName("should return 400 when follower doesn't exist")
    void listPostFollowerNotFoundTest() {
        var inexistentFollowerId = 999;

        given()
            .pathParam("userId", userId)
            .header("followerId", inexistentFollowerId)
        .when()
            .get()
        .then()
            .statusCode(400)
            .body(is("Inexistent followerId"));
    }

    @Test
    @DisplayName("should return 403 when follower isn't follower")
    void listPostNotAFollower() {
        given()
            .pathParam("userId", userId)
            .header("followerId", userNotFollowerId)
        .when()
            .get()
        .then()
            .statusCode(403)
            .body(is("You can´t see these posts"));
    }

    @Test
    @DisplayName("should be return a list of posts")
    void listPostTest() {
        given()
            .pathParam("userId", userId)
            .header("followerId", userFollowerId)
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }
}