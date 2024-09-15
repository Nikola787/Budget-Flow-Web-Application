package com.nikola787.budget_flow_tracker.mapper.transaction;

import com.nikola787.budget_flow_tracker.dto.transaction.TransactionCreateDto;
import com.nikola787.budget_flow_tracker.dto.transaction.TransactionDto;
import com.nikola787.budget_flow_tracker.model.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    public Transaction fromTransactionCreateDto(TransactionCreateDto transactionCreateDto) {
        return Transaction.builder()
                .name(transactionCreateDto.name())
                .date(transactionCreateDto.date())
                .transactionType(transactionCreateDto.transactionType())
                .amount(transactionCreateDto.amount())
                .building(transactionCreateDto.building())
                .build();
    }
    public TransactionDto toTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .name(transaction.getName())
                .date(transaction.getDate())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .building(transaction.getBuilding())
                .build();
    }
}
