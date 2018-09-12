package com.crleon.weatherbit.api.comparator;

import com.crleon.weatherbit.client.domain.Weather;

import java.util.Comparator;

public class WeatherTemperatureComparator implements Comparator<Weather> {

    @Override
    public int compare(Weather weather1, Weather weather2) {
        if (weather1.getTemperature() < weather2.getTemperature()) {
            return -1;
        } else if (weather1.getTemperature() > weather2.getTemperature()) {
            return 1;
        }

        return 0;
    }
}
