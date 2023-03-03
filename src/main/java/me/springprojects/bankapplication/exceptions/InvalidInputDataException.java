package me.springprojects.bankapplication.exceptions;

import me.springprojects.bankapplication.service.enums.DebitExceptions;
import me.springprojects.bankapplication.service.enums.UserExceptions;

public class InvalidInputDataException extends RuntimeException{

    public InvalidInputDataException(String message){
        super(message);
    }

    public InvalidInputDataException(UserExceptions userExceptions){
        super(userExceptions.toString());
    }

    public InvalidInputDataException(DebitExceptions debitExceptions){
        super(debitExceptions.toString());
    }
}
