package com.crleon.weatherbit.client;

import com.crleon.weatherbit.client.configuration.WeatherBitConfiguration;
import com.crleon.weatherbit.client.domain.Forecast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherBitClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherBitClient.class);
    private static final String hourlyForecastEndpoint = "forecast/hourly";

    private WeatherBitConfiguration weatherBitConfiguration;
    private RestTemplate restTemplate;

    @Autowired
    public WeatherBitClient(RestTemplate restTemplate, WeatherBitConfiguration weatherBitConfiguration) {
        this.weatherBitConfiguration = weatherBitConfiguration;
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves the weather forecast for a given amount of hours from weatherbit.io
     * @param hours number of hours to retrieve weather forecast
     * @param postalCode postal code aka zip code
     * @return Forecast forecast containing weather of e
     */
    public Forecast getHourlyForecast(int hours, String postalCode) {
        String urlWithQueryParams = String
                .format("%s%s?key=%s&units=I&hours=%d&postal_code=%s", weatherBitConfiguration.getUrl(),
                        hourlyForecastEndpoint, weatherBitConfiguration.getApiKey(), hours, postalCode);
        Forecast forecast;

        try {
            forecast = restTemplate.getForObject(urlWithQueryParams, Forecast.class);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while making external call to weatherbit.io");
            throw ex;
        }

        return forecast;
    }
}