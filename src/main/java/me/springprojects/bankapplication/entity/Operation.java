package me.springprojects.bankapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import me.springprojects.bankapplication.service.enums.OperationType;

@Entity
@Table(name = "operations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Operation extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "desc")
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

}
