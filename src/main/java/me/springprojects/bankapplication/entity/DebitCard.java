package me.springprojects.bankapplication.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "debit_cards")
@Getter
@Builder
public class DebitCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "debit_card_number")
    private int debitCardNumber;
    @Column(name = "verification_number")
    private int verificationNumber;
    @Column(name = "exp_date")
    private LocalDate expDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User cardOwner;
}
