package com.gabsDev.transactionExecution.Remote.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {

    @Schema(description = "Codifo Http", example = "409")
    private int status;

    @Schema(description = "Error de la excepcion", example = "")
    private String error;

    @Schema(description = "Mensaje de error de la excepcion", example = "")
    private String message;

    @Schema(description = "Fecha en que ocurrio el error", example = "")
    private LocalDateTime timestamp;

}
