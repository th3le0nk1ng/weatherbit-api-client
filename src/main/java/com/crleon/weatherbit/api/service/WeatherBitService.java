package com.crleon.weatherbit.api.service;

import com.crleon.weatherbit.api.comparator.WeatherLocalDateTimeComparator;
import com.crleon.weatherbit.api.comparator.WeatherTemperatureComparator;
import com.crleon.weatherbit.api.exception.InvalidPostalCodeException;
import com.crleon.weatherbit.client.WeatherBitClient;
import com.crleon.weatherbit.client.domain.Forecast;
import com.crleon.weatherbit.client.domain.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("weatherbit")
public class WeatherBitService implements ForecastService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherBitService.class);
    private WeatherBitClient weatherBitClient;

    @Autowired
    public WeatherBitService(WeatherBitClient weatherBitClient) {
        this.weatherBitClient = weatherBitClient;
    }

    @Override
    public Forecast getNextDayHourlyForecastForPostalCode(String postalCode) {
        if (StringUtils.isEmpty(postalCode)) {
            LOGGER.error("Postal code was not provided");
            throw new InvalidPostalCodeException("Postal code was not provided");
        }
        // GET forecast of next 48 hours to handle timezone offset of postal code
        Forecast forecast = weatherBitClient.getHourlyForecast(48, postalCode);

        if (forecast == null) {
            LOGGER.error("Postal code is invalid: {}", postalCode);
            throw new InvalidPostalCodeException(("Postal code is invalid"));
        }

        // Retrieve current local time based on postal code's timezone
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(forecast.getTimezone()));

        // Filter for the full 24 hours of weather forecast for the next day
        List<Weather> weatherList = forecast.getWeatherList().stream()
                .filter(weather -> weather.getLocalDateTime().toLocalDate().isAfter(localDateTime.toLocalDate()))
                .limit(24).sorted(new WeatherLocalDateTimeComparator()).collect(Collectors.toList());
        forecast.setWeatherList(weatherList);

        // Determine the coolest hour of the day from the next day's forecast
        LocalDateTime coolestHourOfDay = forecast.getWeatherList().stream().min(new WeatherTemperatureComparator())
                .get().getLocalDateTime();
        forecast.setCoolestHourOfDay(coolestHourOfDay);

        return forecast;
    }

    @Override
    public Forecast getHourlyForecastForPostalCode(String postalCode) {
        // TODO: Possible future implementations
        return null;
    }

    @Override
    public Forecast getHourlyForecastForCityState(String city, String state) {
        // TODO: Possible future implementations
        return null;
    }
}
