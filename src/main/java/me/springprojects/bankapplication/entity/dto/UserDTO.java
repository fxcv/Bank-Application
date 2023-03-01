package me.springprojects.bankapplication.entity.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class UserDTO {

    private String name;
    private String lastname;
    private String accountNumber;
    private String email;
    private String password;
    private BigDecimal balance;
}
