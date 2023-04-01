package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.FollowerRequest;
import br.com.cronos.redesocial.domain.model.Follower;
import br.com.cronos.redesocial.domain.model.User;
import br.com.cronos.redesocial.domain.repository.FollowerRepository;
import br.com.cronos.redesocial.domain.repository.UserRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestHTTPEndpoint(FollowerResource.class)
class FollowerResourceTest {
    @Inject
    UserRepository userRepository;
    @Inject
    FollowerRepository followerRepository;
    Long userId;
    Long followerId;

    @BeforeEach
    @Transactional
    void setUp() {
        var user = new User();
        user.setAge(30);
        user.setName("joao");
        userRepository.persist(user);
        userId = user.getId();

        var follower = new User();
        follower.setAge(33);
        follower.setName("pedro");
        userRepository.persist(follower);
        followerId = follower.getId();

        var followerEntity = new Follower();
        followerEntity.setFollower(follower);
        followerEntity.setUser(user);
        followerRepository.persist(followerEntity);
    }

    @Test
    @DisplayName("should return 409 when follower is equal to user id")
    void sameUserAsFollowerTest() {
        var follower = new FollowerRequest();
        follower.setFollowerId(userId);

        given()
            .contentType(ContentType.JSON)
            . body(follower)
            .pathParam("userId", userId)
        .when()
            .put()
        .then()
            .statusCode(Response.Status.CONFLICT.getStatusCode())
            .body(is("You can't follower yorself"));
    }

    @Test
    @DisplayName("should return 404 when user id dosen't exists")
    void userNotFoundTest() {
        var inexistentUserId = 999;

        var follower = new FollowerRequest();
        follower.setFollowerId(userId);

        given()
            .contentType(ContentType.JSON)
            . body(follower)
            .pathParam("userId", inexistentUserId)
        .when()
            .put()
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode())
            .body(is("User not found of id 999"));
    }

    @Test
    @DisplayName("should follow a user")
    void followUserTest() {
        var follower = new FollowerRequest();
        follower.setFollowerId(followerId);

        given()
            .contentType(ContentType.JSON)
            . body(follower)
            .pathParam("userId", userId)
        .when()
            .put()
        .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @DisplayName("should return 404 on list user followers and User id doen't exists")
    void userNotFoundWhenListingFollowersTest() {
        var inexistentUserId = 999;

        given()
            .pathParam("userId", inexistentUserId)
        .when()
            .get()
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("should list a user's followers")
    void listFollowersTest() {
        var response = given()
            .contentType(ContentType.JSON)
            .pathParam("userId", userId)
        .when()
            .get()
        .then()
            .extract().response();

        var followersCount = response.jsonPath().get("followerCount");
        var followersContent = response.jsonPath().getList("content");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());
        assertEquals(1, followersCount);
        assertEquals(1, followersContent.size());
    }

    @Test
    @DisplayName("should return 404 on unfollower and User id doen't exists")
    void userNotFoundWhenUnFollowerAUserTest() {
        var inexistentUserId = 999;

        given()
            .pathParam("userId", inexistentUserId)
            .queryParam("followerId", followerId)
        .when()
            .delete()
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("should unfollower a user")
    void unfollowerAUserTest() {
        given()
            .pathParam("userId", userId)
            .queryParam("followerId", followerId)
        .when()
            .delete()
        .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
}