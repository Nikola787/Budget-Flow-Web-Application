package com.nikola787.budget_flow_tracker.service.impl;

import com.nikola787.budget_flow_tracker.dto.PageResponse;
import com.nikola787.budget_flow_tracker.dto.transaction.TransactionCreateDto;
import com.nikola787.budget_flow_tracker.dto.transaction.TransactionDto;
import com.nikola787.budget_flow_tracker.exception.RecordNotFoundException;
import com.nikola787.budget_flow_tracker.exception.RequestNotAuthorizedException;
import com.nikola787.budget_flow_tracker.mapper.transaction.TransactionMapper;
import com.nikola787.budget_flow_tracker.model.authentication.UserProfile;
import com.nikola787.budget_flow_tracker.model.transaction.Transaction;
import com.nikola787.budget_flow_tracker.model.transaction.TransactionType;
import com.nikola787.budget_flow_tracker.repository.TransactionRepository;
import com.nikola787.budget_flow_tracker.service.TransactionService;
import com.nikola787.budget_flow_tracker.service.UserProfileService;
import com.nikola787.budget_flow_tracker.specification.transaction.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final UserProfileService userProfileService;

    @Override
    public Long save(TransactionCreateDto transactionCreateDto, Authentication user) {
        UserProfile userProfile = ((UserProfile) user.getPrincipal());
        Transaction transaction = transactionMapper.fromTransactionCreateDto(transactionCreateDto);
        transaction.setUserProfile(userProfile);
        long amount = transaction.getTransactionType().equals(TransactionType.INCOME) ? transaction.getAmount() : -transaction.getAmount();
        userProfileService.updateBalance(userProfile, amount);
        return transactionRepository.save(transaction).getId();
    }

    @Override
    public PageResponse<TransactionDto> findAllTransactions(int page, int size, Date date, Authentication user) {
        UserProfile userProfile = ((UserProfile) user.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Transaction> transactions = transactionRepository.findAll(TransactionSpecification.withUserId(userProfile.getId(), date), pageable);
        List<TransactionDto> transactionDtoList = transactions
                .stream()
                .map(transactionMapper::toTransactionDto)
                .toList();

        return new PageResponse<>(
                transactionDtoList,
                transactions.getNumber(),
                transactions.getSize(),
                transactions.getTotalElements(),
                transactions.getTotalPages(),
                transactions.isFirst(),
                transactions.isLast()
        );
    }

    @Override
    public void delete(long id, Authentication user) {
        UserProfile userProfile = ((UserProfile) user.getPrincipal());
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) {
            throw new RecordNotFoundException("Transaction not found");
        }
        Transaction transaction = transactionOptional.get();
        if (!transaction.getUserProfile().getId().equals(userProfile.getId())) {
            throw new RequestNotAuthorizedException("User does not have permission to delete this transaction");
        }
        long amount = transaction.getTransactionType().equals(TransactionType.INCOME) ? -transaction.getAmount() : transaction.getAmount();
        userProfileService.updateBalance(userProfile, amount);
        transactionRepository.deleteById(id);
    }
}
