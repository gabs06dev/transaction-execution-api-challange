package com.gabsDev.transactionExecution.Remote.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalProviderError {

    @Schema(description = "Estatus de la operacion realizada", example = "EXECUTED")
    private String status;

    @Schema(description = "Codigo de error", example = "INSUFFICIENT_FUNDS")
    private String code;

    @Schema(description = "Mensaje del resultado", example = "Transferencia recibida")
    private String message;
}
