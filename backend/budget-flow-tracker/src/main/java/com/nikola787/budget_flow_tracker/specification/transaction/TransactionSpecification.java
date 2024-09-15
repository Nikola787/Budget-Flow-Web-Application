package com.nikola787.budget_flow_tracker.specification.transaction;

import com.nikola787.budget_flow_tracker.model.transaction.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Calendar;
import java.util.Date;

public class TransactionSpecification {

    public static Specification<Transaction> withUserId(Long userId, Date date) {
        return (root, query, criteriaBuilder) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return criteriaBuilder.and(criteriaBuilder.equal(root.get("userProfile").get("id"), userId),
                    criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("date")), calendar.get(Calendar.YEAR)),
                    criteriaBuilder.equal(criteriaBuilder.function("MONTH", Integer.class, root.get("date")), calendar.get(Calendar.MONTH) + 1),
                    criteriaBuilder.equal(criteriaBuilder.function("DAY", Integer.class, root.get("date")), calendar.get(Calendar.DAY_OF_MONTH)));
        };
    }
}
