package me.springprojects.bankapplication.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import me.springprojects.bankapplication.entity.enums.Action;

import java.math.BigDecimal;

@Entity
@Table(name = "operations")
@Getter
@Builder
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "desc")
    @Enumerated(EnumType.STRING)
    private Action action;
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User operationOwner;
}