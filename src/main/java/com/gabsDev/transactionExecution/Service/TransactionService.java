package com.gabsDev.transactionExecution.Service;

import com.gabsDev.transactionExecution.Entity.Transaction;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderRequest;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderResponse;
import com.gabsDev.transactionExecution.Remote.Dto.TransactionRequest;
import com.gabsDev.transactionExecution.Remote.Service.ExternalProviderService;
import com.gabsDev.transactionExecution.Repository.TransactionRepository;
import com.gabsDev.transactionExecution.Specification.TransactionSpecification;
import com.gabsDev.transactionExecution.Validator.TransactionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Servicio para la validacion y registro de transacciones bancarias
 *
 * @author Gustavo Barrera
 * @since 2026-11-10
 */
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionValidator validator;
    private final TransactionRepository repository;
    private final ExternalProviderService externalProvider;

    public Page<Transaction> search( String status, String accountId, String type, Pageable pageable){
        Specification<Transaction> specification =
                Specification.allOf(
                        TransactionSpecification.hasStatus(status),
                        TransactionSpecification.hasAccount(accountId),
                        TransactionSpecification.hasType(type)
                );
        return repository.findAll(specification,pageable);
    }

    public Transaction generate(TransactionRequest request) {
        validator.validate(request);
        ProviderResponse response;
            response = externalProvider
                    .execute(new ProviderRequest(request.getAccountId(),request.getType(),
                            request.getAmount(),request.getCurrency()));
       return saveTransaction(response,request);
    }

    public Transaction saveTransaction(ProviderResponse response, TransactionRequest request){
        Transaction transaction = new Transaction();
        transaction.setAccountId(request.getAccountId());
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setDescription(request.getDescription());
        transaction.setStatus(response.getStatus());
        transaction.setProviderTransactionId(response.getTransactionId());
        transaction.setBalanceAfter(response.getBalance());
        transaction.setCreatedAt(LocalDateTime.now());
        repository.save(transaction);
        return transaction;
    }
}
