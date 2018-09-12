package com.crleon.weatherbit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.crleon.weatherbit.api.service.ForecastService;
import com.crleon.weatherbit.client.domain.Forecast;
import com.crleon.weatherbit.constant.IntegrationTestConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { WeatherApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("itest")
public class GetForecastIT {

    @LocalServerPort
    private int port;

    @SpyBean
    ForecastService forecastService;

    @Before
    public void setup() {
        RestAssured.port = port;
        RestAssured.basePath = IntegrationTestConstants.BASE_URL_PATH;
    }

    @Test
    public void getForecastByZipCode_success_200() {
        Forecast forecastResponse = RestAssured.given().get("?postalCode=85006").then().statusCode(200)
                .body("countryCode", equalTo("US")).body("stateCode", equalTo("AZ"))
                .body("weatherList.size()", equalTo(24)).extract().body().as(Forecast.class);

        // Confirm that the first weather record occurs at midnight
        assertThat(forecastResponse.getWeatherList().get(0).getLocalDateTime().getHour(), is(equalTo(0)));
        // Confirm that the last weather record occurs at 23:00
        assertThat(forecastResponse.getWeatherList().get(23).getLocalDateTime().getHour(), is(equalTo(23)));
    }

    @Test
    public void getForecastByZipCode_invalidZipCode_400() {
        RestAssured.given().contentType(ContentType.JSON).get("?postalCode=85jaosidfjaos006").then().statusCode(400)
                .body("errorMessage", is(equalTo("Postal code is invalid.")));
    }

    @Test
    public void getForecastByZipCode_notFound_404() {
        RestAssured.given().contentType(ContentType.JSON).get("/foo?postalCode=85006").then().statusCode(404)
                .body("error", is(equalTo("Not Found")));
    }
}
