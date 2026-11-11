package com.gabsDev.transactionExecution.Validator;

import Utils.Constantes;
import com.gabsDev.transactionExecution.Exception.TransactionException;
import com.gabsDev.transactionExecution.Remote.Dto.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionValidatorTest {

    private final TransactionValidator transactionValidator = new TransactionValidator();

    @Test
    void testValidate_WithInvalidCurrency() {
        TransactionRequest request = mock(TransactionRequest.class);
        when(request.getCurrency()).thenReturn("USD");

        TransactionException exception = assertThrows(
                TransactionException.class,
                () -> transactionValidator.validate(request)
        );

        assert exception.getMessage().equals("Currency is not valid");
        assert exception.getStatus() == HttpStatus.CONFLICT;
    }

    @Test
    void testValidate_WithAmountLessThanOrEqualToOne() {
        TransactionRequest request = mock(TransactionRequest.class);
        when(request.getCurrency()).thenReturn(Constantes.CURRENCY_MXN);
        when(request.getAmount()).thenReturn(BigDecimal.ZERO);

        TransactionException exception = assertThrows(
                TransactionException.class,
                () -> transactionValidator.validate(request)
        );

        assert exception.getMessage().equals("Amount needs to be greater than 1");
        assert exception.getStatus() == HttpStatus.CONFLICT;
    }

    @Test
    void testValidate_WithDebitTypeAndAmountExceedingLimit() {
        TransactionRequest request = mock(TransactionRequest.class);
        when(request.getCurrency()).thenReturn(Constantes.CURRENCY_MXN);
        when(request.getAmount()).thenReturn(BigDecimal.valueOf(15000));
        when(request.getType()).thenReturn(Constantes.TYPE_DEBIT);

        TransactionException exception = assertThrows(
                TransactionException.class,
                () -> transactionValidator.validate(request)
        );

        assert exception.getMessage().equals("Amount cannot exceed 10000");
        assert exception.getStatus() == HttpStatus.CONFLICT;
    }

    @Test
    void testValidate_WithValidRequest() {
        TransactionRequest request = mock(TransactionRequest.class);
        when(request.getCurrency()).thenReturn(Constantes.CURRENCY_MXN);
        when(request.getAmount()).thenReturn(BigDecimal.valueOf(5000));
        when(request.getType()).thenReturn("CREDIT");

        transactionValidator.validate(request);
    }
}