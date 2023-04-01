package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreateUserRequest;
import br.com.cronos.redesocial.api.dto.ResponseError;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class UserResourceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void listAllUsers() {
    }

    @Test
    void get() {
    }

    @Test
    void search() {
    }

    @Test
    @DisplayName("should create a new user sussessfully")
    void createUserTest() {
        var user = new CreateUserRequest();
        user.setName("joao");
        user.setAge(33);

        var response = given()
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .post("/api/v1/users")
        .then()
            .extract().response();

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("id"));
    }

    @Test
    @DisplayName("should return error when json is not valid")
    void createUserErrorTest() {
        var user = new CreateUserRequest();
        user.setName(null);
        user.setAge(null);

        var response = given()
                .contentType(ContentType.JSON)
                .body(user)
            .when()
                .post("/api/v1/users")
            .then()
                .extract().response();

        assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.getStatusCode());
        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertNotNull(errors.get(0).get("message"));
        assertNotNull(errors.get(1).get("message"));
    }

    @Test
    void updateUser() {
    }
}