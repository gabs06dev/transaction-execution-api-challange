package com.gabsDev.transactionExecution.Service;

import com.gabsDev.transactionExecution.Entity.Transaction;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderRequest;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderResponse;
import com.gabsDev.transactionExecution.Remote.Dto.TransactionRequest;
import com.gabsDev.transactionExecution.Remote.Service.ExternalProviderService;
import com.gabsDev.transactionExecution.Repository.TransactionRepository;
import com.gabsDev.transactionExecution.Validator.TransactionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private TransactionValidator validator;

    @Mock
    private ExternalProviderService externalProvider;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearch() {
        Page<Transaction> mockPage = new PageImpl<>(Collections.emptyList());
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(mockPage);

        Page<Transaction> result = transactionService.search("EXECUTED", "acc-123456", "CREDIT", Pageable.unpaged());

        assertEquals(mockPage, result);
        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testGenerate() {
        TransactionRequest request = new TransactionRequest();
        request.setAccountId("acc-123456");
        request.setType("DEBIT");
        request.setAmount(BigDecimal.valueOf(1500));
        request.setCurrency("MXN");

        ProviderResponse response = new ProviderResponse();
        response.setStatus("APPROVED");
        response.setTransactionId("txn-789");
        response.setBalance(BigDecimal.valueOf(5500));

        when(externalProvider.execute(any(ProviderRequest.class))).thenReturn(response);
        when(repository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction result = transactionService.generate(request);

        assertEquals("APPROVED", result.getStatus());
        assertEquals("txn-789", result.getProviderTransactionId());
        verify(validator, times(1)).validate(request);
        verify(externalProvider, times(1)).execute(any(ProviderRequest.class));
        verify(repository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testSaveTransaction() {
        ProviderResponse response = new ProviderResponse();
        response.setStatus("APPROVED");
        response.setTransactionId("txn-789");
        response.setBalance(BigDecimal.valueOf(5500));

        TransactionRequest request = new TransactionRequest();
        request.setAccountId("acc-123456");
        request.setType("CREDIT");
        request.setAmount(BigDecimal.valueOf(1500));
        request.setCurrency("MXN");
        request.setDescription("Test transaction");

        when(repository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction result = transactionService.saveTransaction(response, request);

        assertEquals("APPROVED", result.getStatus());
        assertEquals("txn-789", result.getProviderTransactionId());
        assertEquals(BigDecimal.valueOf(1500), result.getAmount());
        verify(repository, times(1)).save(any(Transaction.class));
    }
}