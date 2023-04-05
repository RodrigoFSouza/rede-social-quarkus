package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreateProfileRequest;
import br.com.cronos.redesocial.api.dto.ResponseError;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestHTTPEndpoint(ProfileResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProfileResourceTest {

    @Test
    @Order(1)
    @DisplayName("should be created a new profile")
    void createProfileTest() {
        var profile = new CreateProfileRequest();
        profile.setName("new");

        var response = given()
                .contentType(ContentType.JSON)
                .body(profile)
            .when()
                .post()
            .then()
                .extract().response();

        assertNotNull(response.jsonPath().getString("id"));
        assertEquals("new", response.jsonPath().getString("name"));
    }

    @Test
    @Order(2)
    @DisplayName("should be not created a new profile")
    void createProfileWithErrorsTest() {
        var profile = new CreateProfileRequest();
        profile.setName(null);

        var response = given()
            .contentType(ContentType.JSON)
            .body(profile)
        .when()
            .post()
        .then()
            .extract().response();

        assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.getStatusCode());
        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertThat(errors.size(), greaterThan(0));
    }

    @Test
    @Order(3)
    @DisplayName("should be list all profiles")
    void listAllProfilesTest() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("size()", is(1));
    }

    @Test
    @Order(4)
    @DisplayName("should be update a profile")
    void updateUserTest() {
        var profile = new CreateProfileRequest();
        profile.setName("update");

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", 1)
            .body(profile)
        .when()
            .put("/{id}")
        .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @Order(5)
    @DisplayName("should be returned a not found status code when id not exists")
    void updateUserNotFoundTest() {
        var profile = new CreateProfileRequest();
        profile.setName("update");

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", 999)
            .body(profile)
        .when()
            .put("/{id}")
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(6)
    @DisplayName("should be not update profile if name not informed")
    void updateUserWithErrrosTest() {
        var profile = new CreateProfileRequest();
        profile.setName(null);

        var response = given()
            .contentType(ContentType.JSON)
            .pathParam("id", 999)
            .body(profile)
        .when()
            .put("/{id}")
        .then()
            .extract().response();

        assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.getStatusCode());
        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertThat(errors.size(), greaterThan(0));
    }

    @Test
    @Order(7)
    @DisplayName("should be deleted a profile if id exists")
    void deleteProfileTest() {
        given()
            .pathParam("id", 1)
        .when()
            .delete("/{id}")
        .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
    @Test
    @Order(8)

    @DisplayName("should be not deleted a profile if id not exists")
    void deleteProfileNotFoundTest() {
        given()
            .pathParam("id", 999)
        .when()
            .delete("/{id}")
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

}