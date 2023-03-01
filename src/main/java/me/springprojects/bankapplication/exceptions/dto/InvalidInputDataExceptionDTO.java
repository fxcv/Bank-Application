package me.springprojects.bankapplication.exceptions.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class InvalidInputDataExceptionDTO {

    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime time;
}
