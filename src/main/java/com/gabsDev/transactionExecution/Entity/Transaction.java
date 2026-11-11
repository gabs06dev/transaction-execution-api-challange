package com.gabsDev.transactionExecution.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Identificador Unico de la transaccion")
    private UUID id;

    @NotNull
    @Size(max = 50)
    @Column(name = "account_id")
    @Schema(description = "Identificador Unico de la cuenta")
    private String accountId;

    @Size(max = 50)
    @Column(name = "type")
    @Schema(description = "Tipo de transaccion", example = "DEBIT,CREDIT")
    private String type;

    @NotNull
    @Column(name = "amount")
    @Schema(description = "Monto de la transaccion", example = "1500")
    private BigDecimal amount;

    @Size(max = 3)
    @Column(name = "currency")
    @Schema(description = "Moneda de la trasaccion a realizar", example = "MXN")
    private String currency;

    @Size(max = 100)
    @Column(name = "description")
    @Schema(description = "Descripcion de la transaccion realizada", example = "Transferencia realizada exitosamente")
    private String description;

    @NotNull
    @Size(max = 50)
    @Column(name = "status")
    @Schema(description = "Estatus de la transaccion", example = "EXECUTED, REFUSED")
    private String status;

    @Size(max = 50)
    @Column(name = "provider_transaction_id")
    @Schema(description = "Identificador unido del proveedor externo", example = "txn-789")
    private String providerTransactionId;

    @Column(name = "balance_after")
    @Schema(description = "Saldo despues de aplicar la transaccion", example = "5500")
    private BigDecimal balanceAfter;

    @Column(name = "created_at")
    @Schema(description = "Fecha de creacion de la transaccion", example = "2025-03-15T10:30:00Z")
    private LocalDateTime createdAt;
}
