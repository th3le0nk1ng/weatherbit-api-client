package com.crleon.weatherbit.client.configuration;

import com.crleon.weatherbit.client.exception.RestTemplateResponseErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @Autowired
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
            RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplate();
        //return restTemplateBuilder.errorHandler(restTemplateResponseErrorHandler).build();
    }

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}