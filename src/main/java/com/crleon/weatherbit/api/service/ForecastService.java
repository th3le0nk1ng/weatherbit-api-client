package com.crleon.weatherbit.api.service;

import com.crleon.weatherbit.client.domain.Forecast;

public interface ForecastService {
    Forecast getNextDayHourlyForecastForPostalCode(String postalCode);
    Forecast getHourlyForecastForPostalCode(String postalCode);
    Forecast getHourlyForecastForCityState(String city, String state);
}
