package me.springprojects.bankapplication.entity.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DebitCardDTO {

    private String debitCardNumber;
    private LocalDate expDate;
}
