package com.crleon.weatherbit.client;

import com.crleon.weatherbit.client.configuration.WeatherBitConfiguration;
import com.crleon.weatherbit.client.domain.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherBitClient {
    private static final String hourlyForecastEndpoint = "forecast/hourly";

    private WeatherBitConfiguration weatherBitConfiguration;
    private RestTemplate restTemplate;

    @Autowired
    public WeatherBitClient(RestTemplate restTemplate, WeatherBitConfiguration weatherBitConfiguration) {
        this.weatherBitConfiguration = weatherBitConfiguration;
        this.restTemplate = restTemplate;
    }

    public Forecast getHourlyForecast(int hours, String postalCode) {
        String urlWithQueryParams = String
                .format("%s%s?key=%s&units=I&hours=%d&postal_code=%s", weatherBitConfiguration.getUrl(),
                        hourlyForecastEndpoint, weatherBitConfiguration.getApiKey(), hours, postalCode);
        Forecast forecast;

        try {
            forecast = restTemplate.getForObject(urlWithQueryParams, Forecast.class);
        } catch (Exception ex) {
            throw ex;
        }

        return forecast;
    }
}