package com.gabsDev.transactionExecution.Service;

import com.gabsDev.transactionExecution.Entity.Transaction;
import com.gabsDev.transactionExecution.Repository.TransactionRepository;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FailedTransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private FailedTransactionService failedTransactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFailedTransaction() {
        ProviderRequest request = new ProviderRequest();
        request.setType("CREDIT");
        request.setAccountId("acc-123456");
        request.setAmount(BigDecimal.valueOf(1500));
        request.setCurrency("MXN");

        String status = "FAILED";
        String errorMessage = "Insufficient funds";

        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
        failedTransactionService.saveFailedTransaction(request, status, errorMessage);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}