package com.crleon.weatherbit.api.service;

import com.crleon.weatherbit.client.domain.Forecast;

public interface ForecastService {

    /**
     * Retrieves the weather forecast of 24 hours for the following day of a given postal code
     * @param postalCode postal code aka zip code
     * @return Forecast forecast containing weather of 24 hours for following day
     */
    Forecast getNextDayHourlyForecastForPostalCode(String postalCode);

    /**
     * Retrieves the weather forecast for the next 24 hours of a given postal code
     * @param postalCode postal code aka zip code
     * @return Forecast forecast containing weather for next 24 hours
     */
    Forecast getHourlyForecastForPostalCode(String postalCode);

    /**
     * Retrieves the weather forecast for the next 24 hours of a given city/state
     * @param city city to lookup forecast
     * @param state state to lookup forecast
     * @return Forecast forecast containing weather for next 24 hours
     */
    Forecast getHourlyForecastForCityState(String city, String state);
}
