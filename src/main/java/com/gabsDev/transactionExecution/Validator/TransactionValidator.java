package com.gabsDev.transactionExecution.Validator;

import Utils.Constantes;
import com.gabsDev.transactionExecution.Exception.TransactionException;
import com.gabsDev.transactionExecution.Remote.Dto.TransactionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static Utils.Constantes.TYPE_DEBIT;


/**
 * Clase responsable de la validacion de reglas de negocio de una transaccion
 *
 * @author Gustavo Barrera
 * @since 2026-11-10
 */
@Component
public class TransactionValidator {

    public void validate(TransactionRequest request) {

        if (!request.getCurrency().equals(Constantes.CURRENCY_MXN)) {
            throw new TransactionException("Currency is not valid", HttpStatus.BAD_REQUEST);
        }

        if (request.getAmount().compareTo(BigDecimal.ONE) <= 0) {
            throw new TransactionException("Amount needs to be greater than 1", HttpStatus.BAD_REQUEST);
        }

        if (request.getType().equals(TYPE_DEBIT)) {
            if (request.getAmount().compareTo(BigDecimal.valueOf(10000)) > 0) {
                throw new TransactionException(
                        "Amount cannot exceed 10000",
                        HttpStatus.BAD_REQUEST
                );
            }
        }
    }
}

