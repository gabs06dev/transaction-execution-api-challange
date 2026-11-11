package com.gabsDev.transactionExecution.Remote.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProviderResponse {

    @Schema(description = "Identificador Unico de la transaccion", example = "txn-789")
    private String transactionId;

    @Schema(description = "Estatus de la operacion realizada", example = "APPROVED")
    private String status;

    @Schema(description = "Saldo despues de aplicar la operacion realizada", example = "5500")
    private BigDecimal balance;

    @Schema(description = "Fecha en que se realizo la operacion", example = "2025-03-15T10:30:00Z")
    private LocalDateTime executedAt;

    @Schema(description = "Mensaje de respuesta del proveedor", example = "2025-03-15T10:30:00Z")
    private String message;

    @Schema(description = "Codigo de resultado / error", example = "REJECTED")
    private String code;
}
