package com.gabsDev.transactionExecution.Specification;

import com.gabsDev.transactionExecution.Entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase tipo Specification para la definicion de filtros para las transacciones
 *
 * @author Gustavo Barrera
 * @since 2026-11-10
 */
public class TransactionSpecification {

    public static Specification<Transaction> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                status == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Transaction> hasAccount(String account) {
        return (root, query, criteriaBuilder) ->
                account == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("accountId"), account);
    }

    public static Specification<Transaction> hasType(String type) {
        return (root, query, criteriaBuilder) ->
                type == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.equal(root.get("type"), type);
    }
}