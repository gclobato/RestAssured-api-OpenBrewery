package api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BreweryApiTest {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://api.openbrewerydb.org/v1";
    }

    @Test
    public void shouldReturnListOfBreweries() {

        given()
                .queryParam("per_page", 3)

        .when()
                .get("/breweries")

        .then()
                .statusCode(200)
                .body("size()", is(3))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].country", equalTo("United States"))
                .time(lessThan(3000L));
    }

    @Test
    public void shouldFilterBreweriesByCity() {

        given()
                .queryParam("by_city", "austin")

        .when()
                .get("/breweries")

        .then()
                .statusCode(200)
                .body("city", everyItem(equalToIgnoringCase("Austin")));
    }

    @Test
    public void shouldFilterBreweriesByType() {

        given()
                .queryParam("by_type", "micro")

        .when()
                .get("/breweries")

        .then()
                .statusCode(200)
                .body("[0].brewery_type", equalTo("micro"));
    }
}
