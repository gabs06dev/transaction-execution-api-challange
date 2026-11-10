package com.gabsDev.transactionExecution.Facade;

import com.gabsDev.transactionExecution.Entity.Transaction;
import com.gabsDev.transactionExecution.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionFacade {

    private final TransactionRepository repository;

    public Iterable<Transaction> findAll(Pageable page){
        return repository.findAll(page);
    }

}
