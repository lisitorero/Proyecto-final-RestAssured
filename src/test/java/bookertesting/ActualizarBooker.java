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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/* Escenarios UnhappyPath para el Endpoint de Actualizar-booker_id */

public class ActualizarBooker {

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

    /* Cuando se elimina uno de los campos del body request */

    @Test
    public void b_UpdateBooker() {

        Map<String, Object> booking = new HashMap<>();
        Map<String, Object> bookingdates = new HashMap<>();
        idBooking = "5";
        booking.put("firstname", "Jim");
        booking.put("lastname", "Brown");
        booking.put("depositpaid", true);

        bookingdates.put("checkin", "2018-01-01");
        bookingdates.put("checkout", "2019-01-01");

        booking.put("bookingdates", bookingdates);
        booking.put("additionalneeds", "Breakfast");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Cookie", "token=" + accessToken);

        given().log().all().contentType(ContentType.JSON).pathParams("id", idBooking).body(booking)
                .headers(headers)
                .when().put("/booking/{id}")
                .then().log().all().assertThat().statusCode(400);

    }
    /* Cuando se manda un campo vacío del body request */
    @Test
    public void c_UpdateBooker() {

        Map<String, Object> booking = new HashMap<>();
        Map<String, Object> bookingdates = new HashMap<>();
        idBooking = "5";
        booking.put("firstname", "Jim");
        booking.put("lastname", "Brown");
        booking.put("totalprice","");
        booking.put("depositpaid", true);

        bookingdates.put("checkin", "2018-01-01");
        bookingdates.put("checkout", "2019-01-01");

        booking.put("bookingdates", bookingdates);
        booking.put("additionalneeds", "Breakfast");

        Map<String, Object> headers = new HashMap<>();
        headers.put("Cookie", "token=" + accessToken);

        given().log().all().contentType(ContentType.JSON).pathParams("id", idBooking).body(booking)
                .headers(headers)
                .when().put("/booking/{id}")
                .then().log().all().assertThat().statusCode(403);

    }



}
