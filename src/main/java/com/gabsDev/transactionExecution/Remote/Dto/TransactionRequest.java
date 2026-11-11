package com.gabsDev.transactionExecution.Remote.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {

    @NotNull
    @Schema(description = "Identificar unico de la cuenta", example = "acc-123456")
    private String accountId;

    @NotNull
    @Schema(description = "Tipo de cuenta", example = "DEBIT")
    private String type;

    @NotNull
    @Schema(description = "Monto de la operacion", example = "1500")
    private BigDecimal amount;

    @NotNull
    @Schema(description = "Moneda de la operacion", example = "MXN")
    private String currency;

    @NotNull
    @Size(max = 50)
    @Schema(description = "Descripcion de la operacion realizada", example = "Transferencia Realizada")
    private String description;
}
