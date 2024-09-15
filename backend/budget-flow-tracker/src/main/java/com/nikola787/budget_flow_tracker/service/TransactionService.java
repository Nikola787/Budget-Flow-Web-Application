package com.nikola787.budget_flow_tracker.service;

import com.nikola787.budget_flow_tracker.dto.PageResponse;
import com.nikola787.budget_flow_tracker.dto.transaction.TransactionCreateDto;
import com.nikola787.budget_flow_tracker.dto.transaction.TransactionDto;
import org.springframework.security.core.Authentication;

import java.util.Date;


public interface TransactionService {

    Long save(TransactionCreateDto transactionCreateDto, Authentication user);

    PageResponse<TransactionDto> findAllTransactions(int page, int size, Date date, Authentication user);

    void delete(long id, Authentication user);
}
