package com.gabsDev.transactionExecution.Remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabsDev.transactionExecution.Exception.ExternalProviderException;
import com.gabsDev.transactionExecution.Remote.Dto.ExternalProviderError;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderRequest;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderResponse;
import com.gabsDev.transactionExecution.Remote.Repository.ExternalProviderRepository;
import com.gabsDev.transactionExecution.Remote.Service.ExternalProviderService;
import com.gabsDev.transactionExecution.Service.FailedTransactionService;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExternalProviderServiceTest {

    @Mock
    private ExternalProviderRepository externalProvider;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private FailedTransactionService failedTransactionService;

    @InjectMocks
    private ExternalProviderService externalProviderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_Success() {
        ProviderRequest request = new ProviderRequest();
        ProviderResponse response = new ProviderResponse();
        when(externalProvider.applyTransaction(any(ProviderRequest.class))).thenReturn(response);
        ProviderResponse result = externalProviderService.execute(request);
        assertEquals(response, result);
        verify(externalProvider, times(1)).applyTransaction(any(ProviderRequest.class));
    }

    @Test
    void testFallback_WithExternalProviderException() {
        ProviderRequest request = new ProviderRequest();
        ExternalProviderException exception = new ExternalProviderException("Error", HttpStatus.CONFLICT);
        ExternalProviderException thrown = assertThrows(
                ExternalProviderException.class,
                () -> externalProviderService.fallback(request, exception)
        );
        assertEquals("Error", thrown.getMessage());
        assertEquals(HttpStatus.CONFLICT, thrown.getHttpStatus());
    }


    @Test
    void testParseError_WithValidError() throws JsonProcessingException {
        ProviderRequest request = new ProviderRequest();
        FeignException feignException = mock(FeignException.class);
        ExternalProviderError error = new ExternalProviderError();
        error.setStatus("REJECTED");
        error.setMessage("The account does not have enough balance to complete the\ntransaction");
        error.setCode("INSUFFICIENT_FUNDS");

        when(feignException.contentUTF8()).thenReturn("{\"status\":\"REJECTED\",\"message\":\"The account does not have enough balance to complete the\\ntransaction\",\"code\":\"INSUFFICIENT_FUNDS\"}");
        when(objectMapper.readValue(anyString(), eq(ExternalProviderError.class))).thenReturn(error);
        doNothing().when(failedTransactionService).saveFailedTransaction(any(), any(), any());

        ExternalProviderException thrown = assertThrows(
                ExternalProviderException.class,
                () -> externalProviderService.parseError(request, feignException)
        );

        assertEquals(HttpStatus.CONFLICT, thrown.getHttpStatus());
        verify(failedTransactionService, times(1)).saveFailedTransaction(any(), eq("REJECTED"), eq("The account does not have enough balance to complete the transaction"));
    }
}