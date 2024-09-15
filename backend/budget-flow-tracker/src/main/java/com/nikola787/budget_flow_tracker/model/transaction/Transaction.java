package com.nikola787.budget_flow_tracker.model.transaction;

import com.nikola787.budget_flow_tracker.model.authentication.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userprofile_id")
    private UserProfile userProfile;
    private String name;
    private Date date;
    private TransactionType transactionType;
    private long amount;
    private String building;

}
