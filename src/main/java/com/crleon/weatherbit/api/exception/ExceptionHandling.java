package com.crleon.weatherbit.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandling {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandling.class);

    /**
     * Handles an unsupported media type exception.
     *
     * @param ex exception to be handled
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(value = { InvalidMediaTypeException.class, HttpMediaTypeNotSupportedException.class})
    public void handleUnsupportedMediaTypeException(Exception ex) {
        /*
         * This method doesn't return any response body, exception or error can
         * be identified by HTTP status code returned.
         */
        LOGGER.error("Unsupported or invalid media type exception occurred ", ex);
    }

    /**
     * Handles a bad request exception.
     *
     * @param ex exception to be handled
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { MissingServletRequestParameterException.class, ServletRequestBindingException.class,
            MissingServletRequestPartException.class, BindException.class})
    public void handleBadRequestException(Exception ex) {
        /*
         * This method doesn't return any response body, exception or error can
         * be identified by HTTP status code returned.
         */
        LOGGER.error("Request parameter binding exception occurred ", ex);
    }

    /**
     * Handles a bad request exception.
     *
     * @param ex exception to be handled
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { InvalidPostalCodeException.class })
    @ResponseBody
    public OperationError handleInvalidPostalCodeException(Exception ex) {
        /*
         * This method doesn't return any response body, exception or error can
         * be identified by HTTP status code returned.
         */
        LOGGER.error("Postal code is invalid", ex);

        OperationError operationError = new OperationError();
        operationError.setErrorMessage("Postal code is invalid.");
        return operationError;
    }

    /**
     * Handles a method not allowed exception.
     *
     * @param ex exception to be handled
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public void handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        /*
         * This method doesn't return any response body, exception or error can
         * be identified by HTTP status code returned.
         */
        LOGGER.error("Unsupported request method exception occurred ", ex);
    }

    /**
     * Handles an internal server exception.
     *
     * @param ex exception to be handled
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = { Exception.class, MissingPathVariableException.class, ConversionNotSupportedException.class,
            HttpMessageNotWritableException.class})
    @ResponseBody
    public void handleInternalServerErrorException(Exception ex) {
        /*
         * This method doesn't return any response body, exception or error can
         * be identified by HTTP status code returned.
         */
        LOGGER.error("Other exception occurred ", ex);
    }

    /**
     * Handles an exception that is thrown when the requested url mapping is not found.
     *
     * @param ex exception to be handled
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = { NoHandlerFoundException.class})
    @ResponseBody
    public OperationError handleNotFoundException(Exception ex) {
        /*
         * This method doesn't return any response body, exception or error can
         * be identified by HTTP status code returned.
         */
        LOGGER.error("Resource not found exception occurred ", ex);

        OperationError operationError = new OperationError();
        operationError.setErrorMessage("Resource not found.");
        return operationError;
    }

    /**
     * Handles an exception that is thrown when the media type is not acceptable
     *
     * @param ex exception to be handled
     */
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    public void handleMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {
        /*
         * This method doesn't return any response body, exception or error can
         * be identified by HTTP status code returned.
         */
        LOGGER.error("Media type not acceptable exception occurred ", ex);
    }
}
