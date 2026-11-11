package com.gabsDev.transactionExecution.Specification;

import com.gabsDev.transactionExecution.Entity.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionSpecificationTest {

    @Mock
    private Root<Transaction> root = mock(Root.class);

    @Mock
    private CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);

    @Test
    void testHasStatus_WithNullStatus() {
        Specification<Transaction> specification = TransactionSpecification.hasStatus(null);
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.conjunction()).thenReturn(mockPredicate);

        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, null, criteriaBuilder));
    }

    @Test
    void testHasStatus_WithValidStatus() {
        Specification<Transaction> specification = TransactionSpecification.hasStatus("APPROVED");
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.equal(root.get("status"), "APPROVED")).thenReturn(mockPredicate);

        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, null, criteriaBuilder));
    }

    @Test
    void testHasAccount_WithNullAccount() {
        Specification<Transaction> specification = TransactionSpecification.hasAccount(null);
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.conjunction()).thenReturn(mockPredicate);

        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, null, criteriaBuilder));
    }

    @Test
    void testHasAccount_WithValidAccount() {
        Specification<Transaction> specification = TransactionSpecification.hasAccount("acc-123456");
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.equal(root.get("accountId"), "acc-123456")).thenReturn(mockPredicate);

        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, null, criteriaBuilder));
    }

    @Test
    void testHasType_WithNullType() {
        Specification<Transaction> specification = TransactionSpecification.hasType(null);
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.conjunction()).thenReturn(mockPredicate);

        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, null, criteriaBuilder));
    }

    @Test
    void testHasType_WithValidType() {
        Specification<Transaction> specification = TransactionSpecification.hasType("CREDIT");
        Predicate mockPredicate = mock(Predicate.class);
        when(criteriaBuilder.equal(root.get("type"), "CREDIT")).thenReturn(mockPredicate);

        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, null, criteriaBuilder));
    }
}