package com.crleon.weatherbit.api.exception;

import java.io.Serializable;

public class OperationError implements Serializable {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
