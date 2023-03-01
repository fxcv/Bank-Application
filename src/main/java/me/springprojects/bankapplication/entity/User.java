package me.springprojects.bankapplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String lastname;
    @Column(name = "account_number")
    private long accountNumber;
    private boolean locked;
    private String email;
    private String password;
    private BigDecimal balance;

    @OneToMany(mappedBy = "operationOwner")
    private List<Operation> operations;

    @OneToMany(mappedBy = "cardOwner")
    private List<DebitCard> cards;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"),
                                        inverseJoinColumns = @JoinColumn(name = "auth_id"))
    private List<Authority> authorities;

}
