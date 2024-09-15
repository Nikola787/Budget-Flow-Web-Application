package com.nikola787.budget_flow_tracker.controller.transaction;

import com.nikola787.budget_flow_tracker.dto.PageResponse;
import com.nikola787.budget_flow_tracker.dto.transaction.TransactionCreateDto;
import com.nikola787.budget_flow_tracker.dto.transaction.TransactionDto;
import com.nikola787.budget_flow_tracker.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Long> saveTransaction(
            @RequestBody @Valid TransactionCreateDto transactionCreateDto,
            Authentication user
    ) {
        return ResponseEntity.ok(transactionService.save(transactionCreateDto, user));
    }

    @GetMapping
    public ResponseEntity<PageResponse<TransactionDto>> findAllTransactionsByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            Authentication user
    ) {
        return ResponseEntity.ok(transactionService.findAllTransactions(page, size, date, user));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Object> deleteTransaction(
            @PathVariable Long id,
            Authentication user
    ) {
        transactionService.delete(id, user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
