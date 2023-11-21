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
import static org.hamcrest.core.IsEqual.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/* Escenarios UnhappyPath para el Endpoint de Consuoltar-booker_id */

public class Consultar_booker_id {

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

    /* cuando el valor del id = 0 */

    @Test
    public void a_GetBooker() {
        idBooking ="0";
        given().log().all().contentType(ContentType.JSON).pathParams("id", idBooking)
                .when().get("/booking/{id}")
                .then().log().all().assertThat().statusCode(404);
    }


    /* cuando el valor del id =  es una letra alfanumérica */

    @Test
    public void b_GetBooker() {
        idBooking ="a";
        given().log().all().contentType(ContentType.JSON).pathParams("id", idBooking)
                .when().get("/booking/{id}")
                .then().log().all().assertThat().statusCode(404);
    }

}

