package com.gabsDev.transactionExecution.Exception;

import com.gabsDev.transactionExecution.Remote.Dto.ResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleBusinessException() {
        TransactionException exception = mock(TransactionException.class);
        when(exception.getStatus()).thenReturn(HttpStatus.CONFLICT);
        when(exception.getMessage()).thenReturn("Transaction error occurred");

        ResponseEntity<ResponseModel> response = globalExceptionHandler.handleBusinessException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Transaction error occurred", response.getBody().getMessage());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), response.getBody().getError());
    }

    @Test
    void testHandleExternalException() {
        ExternalProviderException exception = mock(ExternalProviderException.class);
        when(exception.getHttpStatus()).thenReturn(HttpStatus.BAD_GATEWAY);
        when(exception.getMessage()).thenReturn("External provider error occurred");

        ResponseEntity<ResponseModel> response = globalExceptionHandler.handleExternalException(exception);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertEquals("External provider error occurred", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_GATEWAY.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.BAD_GATEWAY.getReasonPhrase(), response.getBody().getError());
    }
}