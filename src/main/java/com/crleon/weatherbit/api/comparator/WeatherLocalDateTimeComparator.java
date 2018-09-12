package com.crleon.weatherbit.api.comparator;

import com.crleon.weatherbit.client.domain.Weather;

import java.util.Comparator;

public class WeatherLocalDateTimeComparator implements Comparator<Weather> {

    @Override
    public int compare(Weather weather1, Weather weather2) {
        return weather1.getLocalDateTime().compareTo(weather2.getLocalDateTime());
    }
}
