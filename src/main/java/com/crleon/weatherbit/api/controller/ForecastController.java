package com.crleon.weatherbit.api.controller;

import com.crleon.weatherbit.api.service.ForecastService;
import com.crleon.weatherbit.client.domain.Forecast;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/forecast")
@Api(value = "forecast", description = "Operations pertaining to weather forecasts")
public class ForecastController {

    private static final String DATE_FORMATTER_ISO_LOCAL_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
    private ForecastService forecastService;

    @Autowired
    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @ApiOperation(value = "Get next day forecast")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved weather forecast"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 415, message = "The file type uploaded is unsupported") })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Forecast getForecast(
            @ApiParam(value = "Postal code", required = true) @RequestParam(name = "postalCode") String postalCode,
            @RequestParam(name = "hours", defaultValue = "48") int hours,
            @RequestParam(name = "nextDay", defaultValue = "true") boolean nextDay) {
        return forecastService.getNextDayHourlyForecastForPostalCode(postalCode);
    }
}
