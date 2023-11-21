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
public class CrearBooker {

    final String url = "https://restful-booker.herokuapp.com";
    final String username = "admin";
    final String password = "password123";

    public static String accessToken;
    public static String idBooking;

    /* Escenarios UnhappyPath para el Endpoint Creación de Booker
     */

    @Before
    public void setUp() {
        RestAssured.config = new RestAssuredConfig().
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.baseURI = url;
    }

    /*Cuando se manda un body incompleto */
    @Test
    public void b_CreateBooker() {
        Map<String, Object> booking = new HashMap<>();
        Map<String, Object> bookingdates = new HashMap<>();

        booking.put("lastname", "Brown");
        booking.put("totalprice", "mil");
        booking.put("depositpaid", true);

        bookingdates.put("checkin", "2018-01-01");
        bookingdates.put("checkout", "2019-01-01");

        booking.put("bookingdates", bookingdates);
        booking.put("additionalneeds", "Breakfast");

              given().log().all().contentType(ContentType.JSON).body(booking)
                .when().post("/booking")
                .then().log().all().assertThat().statusCode(500);
            }

}
