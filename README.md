# Spring Boot Weather Forecast REST API and Client leveraging Weatherbit.io API

### This application is a sample project of a REST API and client that can return the following
* Full 24 hour forecast for the next day based on postal code
* Coolest temperature and time of day for the next day forecast

## Prerequisites
- JDK 1.8+
- Gradle 4.8

## Stack
- Spring Boot
- Swagger

#### How to Run?
The `gradlew` executable can be used to build the project without needing to install Gradle.

```
gradle clean build OR ./gradlew clean build

java -jar builds/libs/weatherbit-io-1.0.0.jar
```

#### Health Check
To view a health check of the application go to http://localhost:8080/health.

#### Swagger
To explore and invoke the REST endpoints go to http://localhost:8080/swagger-ui.html.

#### Client package
The code in the client package can be moved to a separate repo and included as a dependency in this API (as well as others).
