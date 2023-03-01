package me.springprojects.bankapplication.exceptions;

public class InvalidInputDataException extends RuntimeException{

    public InvalidInputDataException(String message){
        super(message);
    }
}
