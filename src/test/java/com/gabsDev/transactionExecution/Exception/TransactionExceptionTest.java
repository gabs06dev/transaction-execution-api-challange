package com.gabsDev.transactionExecution.Exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionExceptionTest {

    @Test
    void testTransactionException_WithValidMessageAndStatus() {
        String message = "Transaction error occurred";
        HttpStatus status = HttpStatus.CONFLICT;

        TransactionException exception = new TransactionException(message, status);

        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getStatus());
    }

    @Test
    void testTransactionException_WithDifferentStatus() {
        String message = "Another transaction error";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        TransactionException exception = new TransactionException(message, status);

        assertEquals(message, exception.getMessage());
        assertEquals(status, exception.getStatus());
    }
}
