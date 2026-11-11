package com.gabsDev.transactionExecution.Controller;

import com.gabsDev.transactionExecution.Entity.Transaction;
import com.gabsDev.transactionExecution.Remote.Dto.ResponseModel;
import com.gabsDev.transactionExecution.Remote.Dto.TransactionRequest;
import com.gabsDev.transactionExecution.Service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador dedicado a las transacciones bancarias y sus servicios
 *
 * @author Gustavo Barrera
 * @since 2026-11-10
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Consulta Exitosa")
    @Operation(summary = "Consulta de operaciones", description = "Consulta de operaciones bancarias filtradas por estado, cuenta y tipo de transacción")
    public ResponseEntity<Page<Transaction>> search(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String accountId,
            @RequestParam(required = false) String type,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(
                service.search(status, accountId, type, pageable)
        );
    }

    @PostMapping
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Registro de transacción exitoso"),
            @ApiResponse(responseCode = "422", description = "Error al ejecutar registro"),})
    @Operation(summary = "Registro de transacciones",
            description = "Registro de transacciones bancarias, validando reglas de negocio y comunicándose con un proveedor externo para la aprobación de la transacción")
    public ResponseEntity<Transaction> registrar(@RequestBody @Valid TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.generate(request));
    }
}
