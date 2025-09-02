import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasKey;

public class AuthenticationTests {

    @Test
    public void createAuthTokenWithValidCredentialsTest() throws JsonProcessingException {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Usando el mismo estilo del código base - sin Lombok en los tests
        String payload = "{\"username\":\"admin\",\"password\":\"password123\"}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when().post("/auth");

        response.then().assertThat().statusCode(200);
        response.then().log().body();

        response.then().assertThat().body("$", hasKey("token"));
        response.then().assertThat().body("token", Matchers.notNullValue());
        response.then().assertThat().body("token", Matchers.not(Matchers.equalTo("")));
    }

    @Test
    public void createAuthTokenWithInvalidUsernameTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String payload = "{\"username\":\"invalidUser\",\"password\":\"password123\"}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when().post("/auth");

        response.then().assertThat().statusCode(200);
        response.then().log().body();
    }

    @Test
    public void createAuthTokenWithInvalidPasswordTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String payload = "{\"username\":\"admin\",\"password\":\"wrongpassword\"}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when().post("/auth");

        response.then().assertThat().statusCode(200);
        response.then().log().body();
    }

    @Test
    public void createAuthTokenWithMissingUsernameTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String payload = "{\"password\":\"password123\"}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when().post("/auth");

        response.then().log().body();
    }

    @Test
    public void createAuthTokenWithMissingPasswordTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String payload = "{\"username\":\"admin\"}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when().post("/auth");

        response.then().log().body();
    }
}