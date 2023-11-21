package bookertesting;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
/* Escenarios UnhappyPath para el Endpoint de Delete_id */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeleteBooker {

    final String url = "https://restful-booker.herokuapp.com";
    final String username = "admin";
    final String password = "password123";

    public static String accessToken;
    public static String idBooking;

    @Before
    public void setUp() {
        RestAssured.config = new RestAssuredConfig().
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.baseURI = url;
    }

    @Test
    public void a_GetTokenBooker() {
        Map<String, Object> tokenBooker = new HashMap<>();
        tokenBooker.put("username", username);
        tokenBooker.put("password", password);

        JsonPath jsonPath =  given().log().all().contentType(ContentType.JSON).body(tokenBooker)
                .when().post("/auth")
                .then().log().all().assertThat().statusCode(200).and()
                .body("token", notNullValue()).extract().jsonPath();

        accessToken = jsonPath.getString("token");
    }
/*Cuando el id = 0 */

    public void b_DeleteBooker() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Cookie", "token=" + accessToken);
            idBooking = "0";
        given().log().all().contentType(ContentType.JSON).pathParams("id", idBooking)
                .headers(headers)
                .when().delete("/booking/{id}")
                .then().log().all().assertThat().statusCode(405);
    }

    /*Cuando el id = "a" una letra */
    /*@Test*/
    public void c_DeleteBooker() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Cookie", "token=" + accessToken);
        idBooking = "a";
        given().log().all().contentType(ContentType.JSON).pathParams("id", idBooking)
                .headers(headers)
                .when().delete("/booking/{id}")
                .then().log().all().assertThat().statusCode(405);
    }

    /*Cuando el id = "" es null */

    public void d_DeleteBooker() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Cookie", "token=" + accessToken);
        idBooking = "";
        given().log().all().contentType(ContentType.JSON).pathParams("id", idBooking)
                .headers(headers)
                .when().delete("/booking/{id}")
                .then().log().all().assertThat().statusCode(404);
    }

    /*Cuando el id = no exista */
    @Test
    public void e_DeleteBooker() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Cookie", "token=" + accessToken);
        idBooking = "10000";
        given().log().all().contentType(ContentType.JSON).pathParams("id", idBooking)
                .headers(headers)
                .when().delete("/booking/{id}")
                .then().log().all().assertThat().statusCode(405);
    }
}
