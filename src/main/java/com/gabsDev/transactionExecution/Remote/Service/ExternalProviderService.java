
package com.gabsDev.transactionExecution.Remote.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabsDev.transactionExecution.Exception.ExternalProviderException;
import com.gabsDev.transactionExecution.Remote.Dto.ExternalProviderError;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderRequest;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderResponse;
import com.gabsDev.transactionExecution.Remote.Repository.ExternalProviderRepository;
import com.gabsDev.transactionExecution.Service.FailedTransactionService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static Utils.Constantes.*;


/**
 * Servicio responsable de la comunicacion con proveedor externo para la aplicacion de transacciones
 *
 * @author Gustavo Barrera
 * @since 2026-11-10
 */
@Service
public class ExternalProviderService {
    private final ExternalProviderRepository externalProvider;
    private final ObjectMapper objectMapper;
    private final FailedTransactionService failedTransactionService;

    public ExternalProviderService(
            ExternalProviderRepository externalProvider,
            ObjectMapper objectMapper, FailedTransactionService failedTransactionService) {
        this.externalProvider = externalProvider;
        this.objectMapper = objectMapper;
        this.failedTransactionService = failedTransactionService;
    }

    @CircuitBreaker(name = "transactionProvider", fallbackMethod = "fallback")
    public ProviderResponse execute(ProviderRequest request) {
        try {
            return externalProvider.applyTransaction(request);
        } catch (FeignException ex) {
            parseError(request, ex);
            throw ex;
        }
    }

    public ProviderResponse fallback(ProviderRequest request, Throwable exception) {
        if (exception instanceof ExternalProviderException) {
            throw (ExternalProviderException) exception;
        }
        throw new ExternalProviderException(
                PROVEDOOR_NO_DISPONIBLE,
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    public void parseError(ProviderRequest request, FeignException ex) {
        try {
            ExternalProviderError error = objectMapper.readValue(ex.contentUTF8(), ExternalProviderError.class);
            String sanitizedMessage = error.getMessage().replace("\n", " ");
            failedTransactionService.saveFailedTransaction(
                    request,
                    error.getStatus(),
                    sanitizedMessage
            );
            if (CODE_INSUFFICIENT_FUNDS.equals(error.getCode())) {
                throw new ExternalProviderException(
                        sanitizedMessage,
                        HttpStatus.UNPROCESSABLE_CONTENT
                );
            }

        } catch (JsonProcessingException e) {
            throw new ExternalProviderException(
                    EXCEPCION_RESPUESTA_PROVEEDOR,
                    HttpStatus.BAD_GATEWAY
            );
        }
    }
}
