package com.gabsDev.transactionExecution.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {

    private String accountId;

    private String type;

    private BigDecimal amount;

    private String currency;

    private String description;
}
