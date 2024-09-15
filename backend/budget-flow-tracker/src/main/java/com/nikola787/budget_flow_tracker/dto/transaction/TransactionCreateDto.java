package com.nikola787.budget_flow_tracker.dto.transaction;
import com.nikola787.budget_flow_tracker.model.transaction.TransactionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.apache.logging.log4j.message.Message;

import java.util.Date;

public record TransactionCreateDto(
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z\\s]{3,30}$", message = "Name must contain only letters and spaces, and be between 3 and 30 characters long.")
        String name,

        @NotNull(message = "Date cannot be null.")
        Date date,

        @NotNull(message = "Transaction type cannot be null.")
        TransactionType transactionType,

        @PositiveOrZero(message = "Amount must be a positive value.")
        long amount,

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z\\s]{3,30}$", message = "Location must contain only letters and spaces, and be between 3 and 30 characters long.")
        String building

) {

}
