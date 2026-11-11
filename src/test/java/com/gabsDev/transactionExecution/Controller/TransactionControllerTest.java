package com.gabsDev.transactionExecution.Controller;

import com.gabsDev.transactionExecution.Entity.Transaction;
import com.gabsDev.transactionExecution.Remote.Dto.TransactionRequest;
import com.gabsDev.transactionExecution.Service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionService service;

    private TransactionController controller;

    @BeforeEach
    void setUp() {
        service = mock(TransactionService.class);
        controller = new TransactionController(service);
    }

    @Test
    void testSearch() {
        String status = "APPROVED";
        String accountId = "acc-123456";
        String type = "CREDIT";
        Pageable pageable = mock(Pageable.class);
        Page<Transaction> mockPage = new PageImpl<>(Collections.emptyList());

        when(service.search(status, accountId, type, pageable)).thenReturn(mockPage);

        ResponseEntity<Page<Transaction>> response = controller.search(status, accountId, type, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(service, times(1)).search(status, accountId, type, pageable);
    }

    @Test
    void testRegistrar() {
        TransactionRequest request = mock(TransactionRequest.class);
        Transaction transaction = mock(Transaction.class);

        when(service.generate(request)).thenReturn(transaction);

        ResponseEntity<Transaction> response = controller.registrar(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());
        verify(service, times(1)).generate(request);
    }
}