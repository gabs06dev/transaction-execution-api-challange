package com.gabsDev.transactionExecution.Exception;

import com.gabsDev.transactionExecution.Remote.Dto.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ResponseModel> handleBusinessException(
            TransactionException ex) {

        HttpStatus status = ex.getStatus();

        ResponseModel errorResponse = new ResponseModel(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    @ExceptionHandler(ExternalProviderException.class)
    public ResponseEntity<ResponseModel> handleExternalException(
            ExternalProviderException ex) {

        HttpStatus status = ex.getHttpStatus();

        ResponseModel errorResponse = new ResponseModel(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }

    private ResponseEntity<ResponseModel> buildError(
            HttpStatus status,
            String error,
            String message) {

        ResponseModel response = ResponseModel.builder()
                .status(status.value())
                .error(error)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(status).body(response);
    }
}

