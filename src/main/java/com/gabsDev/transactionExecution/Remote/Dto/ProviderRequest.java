package com.gabsDev.transactionExecution.Remote.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProviderRequest {

    @Schema(description = "Identificar unico de la cuenta", example = "acc-123456")
    private String accountId;

    @Schema(description = "Tipo de cuenta", example = "DEBIT")
    private String type;

    @Schema(description = "Monto de la operacion", example = "1500")
    private BigDecimal amount;

    @Schema(description = "Moneda de la operacion", example = "MXN")
    private String currency;
}
