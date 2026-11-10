package com.gabsDev.transactionExecution.Controller;

import com.gabsDev.transactionExecution.Entity.Transaction;
import com.gabsDev.transactionExecution.Service.TransactionService;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Transaction> obtenerTransacciones(Pageable pageable){
        return service.get(pageable);
    }
}
