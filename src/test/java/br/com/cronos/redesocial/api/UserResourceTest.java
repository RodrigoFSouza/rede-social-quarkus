package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreateUserRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.h2.command.ddl.CreateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

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
    void updateUser() {
    }
}