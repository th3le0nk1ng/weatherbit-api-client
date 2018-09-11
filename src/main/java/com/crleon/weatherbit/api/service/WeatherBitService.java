package com.crleon.weatherbit.api.service;

import com.crleon.weatherbit.api.exception.PostalCodeNotFoundException;
import com.crleon.weatherbit.client.WeatherBitClient;
import com.crleon.weatherbit.client.domain.Forecast;
import com.crleon.weatherbit.client.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("weatherbit")
public class WeatherBitService implements ForecastService {
    private WeatherBitClient weatherBitClient;

    @Autowired
    public WeatherBitService(WeatherBitClient weatherBitClient) {
        this.weatherBitClient = weatherBitClient;
    }

    @Override
    public Forecast getNextDayHourlyForecastForPostalCode(String postalCode) {
        if (postalCode == null) {
            throw new PostalCodeNotFoundException("Postal code was not provided");
        }

        // Query for forecast of next 48 hours to handle timezone offset for "next day" of postal code
        Forecast forecast = weatherBitClient.getHourlyForecast(48, postalCode);

        if (forecast == null) {
            throw new PostalCodeNotFoundException(("Postal code is invalid"));
        }

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(forecast.getTimezone()));
        int hourOffsetFromMidnight = 23 - localDateTime.getHour();

        List<Weather> weatherList = forecast.getWeatherList().stream().skip(hourOffsetFromMidnight).limit(24).collect(Collectors.toList());
        forecast.setWeatherList(weatherList);

        return forecast;
    }

    @Override
    public Forecast getHourlyForecastForPostalCode(String postalCode) {
        return null;
    }

    @Override
    public Forecast getHourlyForecastForCityState(String city, String state) {
        return null;
    }

/*    private Forecast adjustNextDayForecastForTimezoneOffset(Forecast forecast) {
        DateTime dateTime = new DateTime(DateTimeZone.forID(forecast.getTimezone()));
        int hourOffset = 23 - dateTime.getHourOfDay();

        forecast.getWeatherList().stream().filter(w -> dateTime.getDayOfMonth().getLocalTime())
    }*/
}
