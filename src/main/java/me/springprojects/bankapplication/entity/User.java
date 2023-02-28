package me.springprojects.bankapplication.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Entity
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String lastname;
    @Column(name = "account_number")
    private int accountNumber;
    private boolean locked;
    private String email;
    private String password;

    @OneToMany(mappedBy = "operationOwner")
    private List<Operation> operations;

    @OneToMany(mappedBy = "cardOwner")
    private List<DebitCard> cards;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"),
                                        inverseJoinColumns = @JoinColumn(name = "auth_id"))
    private List<Authority> authorities;

}
