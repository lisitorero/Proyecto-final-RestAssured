package bookertesting;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

/* Escenarios UnhappyPath para el Endpoint de Generación de token
 */
public class GenerarToken {


    final String url = "https://restful-booker.herokuapp.com";
    final String username = "admin";
    final String password = "";

    public static String accessToken;
    public static String idBooking;

    @Before
    public void setUp() {
        RestAssured.config = new RestAssuredConfig().
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.baseURI = url;
    }

    /* cuando se manda credenciale vacías */
    @Test
    public void a_GetTokenBooker() {
        Map<String, Object> tokenBooker = new HashMap<>();
        tokenBooker.put("username", username);
        tokenBooker.put("password", password);

        JsonPath jsonPath = given().log().all().contentType(ContentType.JSON).body(tokenBooker)
                .when().post("/auth")
                .then().log().all().assertThat().statusCode(200).and()
                .body("reason", notNullValue(), "reason", equalTo("Bad credentials")).extract().jsonPath();

    }

}
