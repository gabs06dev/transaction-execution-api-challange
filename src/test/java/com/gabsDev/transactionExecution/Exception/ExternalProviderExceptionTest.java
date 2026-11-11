package com.gabsDev.transactionExecution.Exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExternalProviderExceptionTest {

    @Test
    void testExternalProviderException_WithValidMessageAndStatus() {
        String message = "Provider error occurred";
        HttpStatus status = HttpStatus.BAD_GATEWAY;

        ExternalProviderException exception = new ExternalProviderException(message, status);

        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getHttpStatus());
    }

    @Test
    void testExternalProviderException_WithDifferentStatus() {
        String message = "Another provider error";
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;

        ExternalProviderException exception = new ExternalProviderException(message, status);

        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getHttpStatus());
    }
}