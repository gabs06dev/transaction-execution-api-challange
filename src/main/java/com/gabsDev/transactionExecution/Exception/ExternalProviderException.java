package com.gabsDev.transactionExecution.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExternalProviderException extends RuntimeException{
    private final HttpStatus httpStatus;

    public ExternalProviderException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }
}
