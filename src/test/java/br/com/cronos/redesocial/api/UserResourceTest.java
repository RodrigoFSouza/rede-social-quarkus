package br.com.cronos.redesocial.api;

import br.com.cronos.redesocial.api.dto.CreateUserRequest;
import br.com.cronos.redesocial.api.dto.ResponseError;
import br.com.cronos.redesocial.domain.model.Follower;
import br.com.cronos.redesocial.domain.model.User;
import br.com.cronos.redesocial.domain.repository.FollowerRepository;
import br.com.cronos.redesocial.domain.repository.PostRepository;
import br.com.cronos.redesocial.domain.repository.UserRepository;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

    @TestHTTPResource("/api/v1/users")
    URL apiUrl;

    Long idUserCreated;

    @Inject
    UserRepository userRepository;

    @Inject
    FollowerRepository followerRepository;

    @Inject
    PostRepository postRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        // Para garantir que outros testes não vão interferir neste aqui
        postRepository.delete("delete from Post");
        followerRepository.delete("delete from Follower");
        userRepository.delete("delete from User");

        var user = new User();
        user.setAge(44);
        user.setName("jose");
        userRepository.persist(user);
        idUserCreated = user.getId();
    }

    @Test
    @Order(1)
    @DisplayName("sould be empty list when not users")
    void listEmptyUsers() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get(apiUrl)
        .then()
            .statusCode(200)
        .body("size()", is(1));
    }

    @Test
    @DisplayName("should create a new user sussessfully")
    @Order(2)
    void createUserTest() {
        var user = new CreateUserRequest();
        user.setName("joao");
        user.setAge(33);

        var response = given()
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .post(apiUrl)
        .then()
            .extract().response();

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("id"));
    }

    @Test
    @DisplayName("should return error when json is not valid")
    @Order(3)
    void createUserErrorTest() {
        var user = new CreateUserRequest();
        user.setName(null);
        user.setAge(null);

        var response = given()
                .contentType(ContentType.JSON)
                .body(user)
            .when()
                .post(apiUrl)
            .then()
                .extract().response();

        assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.getStatusCode());
        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertNotNull(errors.get(0).get("message"));
        assertNotNull(errors.get(1).get("message"));
    }

    @Test
    @Order(4)
    @DisplayName("sould be list all users")
    void listAllUsers() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get(apiUrl)
        .then()
            .statusCode(200)
            .body("size()", is(1));
    }

    @Test
    @Order(5)
    @DisplayName("sould be not null user retorned")
    void get() {
        given()
                .pathParam("id", idUserCreated)
                .contentType(ContentType.JSON)
        .when()
                .get(apiUrl + "/{id}")
        .then()
                .statusCode(200)
                .body("name", equalTo("jose"))
                .body("age", equalTo(44));
    }

    @Test
    @Order(6)
    @DisplayName("sould be not found status when id of user not exists")
    void getIdNotFound() {
        given()
            .pathParam("id", "999")
            .contentType(ContentType.JSON)
        .when()
            .get(apiUrl + "/{id}")
        .then()
            .statusCode(404);
    }

    @Test
    @Order(7)
    @DisplayName("sould be search of name and user retorned")
    void search() {
        given()
                .pathParam("name", "jose")
                .contentType(ContentType.JSON)
        .when()
            .get(apiUrl + "/search/{name}")
        .then()
            .statusCode(200)
            .body("name", equalTo("jose"))
            .body("age", equalTo(44));
    }

    @Test
    @Order(8)
    @DisplayName("sould be not found status when name of user not exists")
    void searchNotFoundName() {
        given()
            .pathParam("name", "pedro")
            .contentType(ContentType.JSON)
        .when()
            .get(apiUrl + "/search/{name}")
        .then()
            .statusCode(404);
    }

    @Test
    @Order(9)
    @DisplayName("sould be update user")
    void updateUser() {
        var user = new CreateUserRequest();
        user.setName("joao de souza");
        user.setAge(34);

        given()
            .pathParam("id", idUserCreated)
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .put(apiUrl + "/{id}")
        .then()
            .statusCode(204);
    }

    @Test
    @Order(10)
    @DisplayName("sould be update user not found")
    void updateUserNotExists() {
        var user = new CreateUserRequest();
        user.setName("joao de souza");
        user.setAge(34);

        given()
            .pathParam("id", "2")
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .put(apiUrl + "/{id}")
        .then()
            .statusCode(404);
    }

    @Test
    @Order(11)
    @DisplayName("sould be update return invalid fields")
    void updateUserInvalidFields() {
        var user = new CreateUserRequest();
        user.setName(null);
        user.setAge(null);

        var response = given()
            .pathParam("id", "1")
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .put(apiUrl + "/{id}")
        .then()
            .extract().response();

        assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.getStatusCode());
        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertNotNull(errors.get(0).get("message"));
        assertNotNull(errors.get(1).get("message"));
    }

    @Test
    @Order(12)
    @DisplayName("sould be deleted user with exists")
    void deleteUser() {
        given()
            .pathParam("id", idUserCreated)
            .contentType(ContentType.JSON)
        .when()
            .delete(apiUrl + "/{id}")
        .then()
            .statusCode(204);
    }

    @Test
    @Order(13)
    @DisplayName("sould be not deleted user with not exists")
    void deleteUserWithNotExists() {
        given()
            .pathParam("id", "1")
            .contentType(ContentType.JSON)
        .when()
            .delete(apiUrl + "/{id}")
        .then()
            .statusCode(404);
    }
}