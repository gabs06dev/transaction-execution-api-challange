package com.gabsDev.transactionExecution.Service;

import com.gabsDev.transactionExecution.Entity.Transaction;
import com.gabsDev.transactionExecution.Facade.TransactionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionFacade facade;


    @Autowired
    public TransactionService(TransactionFacade facade) {
        this.facade = facade;
    }

    public Iterable<Transaction> get(Pageable pageable) {
        return facade.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by(Sort.Direction.ASC, "accountId")
                )
        );
    }
}
