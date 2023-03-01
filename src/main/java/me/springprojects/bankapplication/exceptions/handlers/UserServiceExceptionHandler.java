package me.springprojects.bankapplication.exceptions.handlers;

import me.springprojects.bankapplication.exceptions.InvalidInputDataException;
import me.springprojects.bankapplication.exceptions.dto.InvalidInputDataExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler(value = InvalidInputDataException.class)
    public ResponseEntity<InvalidInputDataExceptionDTO> handleInvalidInputDataException(InvalidInputDataException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        var exceptionDTO = InvalidInputDataExceptionDTO.builder()
                .message(e.getMessage())
                .httpStatus(httpStatus)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity.status(httpStatus).body(exceptionDTO);
    }
}
