package com.nikola787.budget_flow_tracker.dto.transaction;

import com.nikola787.budget_flow_tracker.model.transaction.TransactionType;
import lombok.Builder;

import java.util.Date;

@Builder
public record TransactionDto(
        long id,
        String name,
        Date date,
        TransactionType transactionType,
        long amount,
        String building
) {
}
