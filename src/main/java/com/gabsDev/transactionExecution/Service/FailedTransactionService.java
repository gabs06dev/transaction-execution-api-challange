package com.gabsDev.transactionExecution.Service;

import com.gabsDev.transactionExecution.Entity.Transaction;
import com.gabsDev.transactionExecution.Remote.Dto.ProviderRequest;
import com.gabsDev.transactionExecution.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Servicio para la el registro en bd de operaciones fallidas
 *
 * @author Gustavo Barrera
 * @since 2026-11-10
 */
@Service
@RequiredArgsConstructor
public class FailedTransactionService {
    private final TransactionRepository repository;

    public void saveFailedTransaction(
            ProviderRequest request,
            String status,
            String errorMessage) {
        Transaction transaction = new Transaction();
        transaction.setType(request.getType());
        transaction.setAccountId(request.getAccountId());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setStatus(status);
        transaction.setDescription(errorMessage);
        transaction.setCreatedAt(LocalDateTime.now());

        repository.save(transaction);
    }
}
