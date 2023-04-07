package br.com.cronos.redesocial.api;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DBRider
@QuarkusTest
@QuarkusTestResource(RedeSocialTestLifecycleManager.class)
@DBUnit(schema = "public", caseSensitiveTableNames = false, cacheConnection = false)
public class AddressResourceTest {
    @Test
    @DataSet(value = "datasets/address.yml")
    public void listAllAddressFromUserTest() {
        var response = given()
            .contentType(ContentType.JSON)
        .when()
            .get("/api/v1/users/address")
        .then()
                .extract().response();

        Approvals.verify(response.asString());
    }
}
