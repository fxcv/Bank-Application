package me.springprojects.bankapplication.service.verification;

import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.dto.UserDTO;
import me.springprojects.bankapplication.exceptions.InvalidInputDataException;
import me.springprojects.bankapplication.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserServiceVerification {

    private final UserRepository userRepository;

    public void verificateInputUserData(UserDTO userDTO){
        if(userDTO.getName() == null || userDTO.getLastname() == null ||
                userDTO.getAccountNumber() == null || userDTO.getEmail() == null ||
                userDTO.getPassword() == null) throw new InvalidInputDataException("Please provide all of the needed data.");

        verificateUserName(userDTO.getName());
        verificateUserLastname(userDTO.getLastname());
        verificateUserAccountNumber(userDTO.getAccountNumber());
        verificateUserEmail(userDTO.getEmail());
        verificateUserPassword(userDTO.getPassword());
    }

    private void verificateUserName(String name){

    }

    private void verificateUserLastname(String lastname){

    }

    private void verificateUserAccountNumber(String accountNumber){

    }

    private void verificateUserEmail(String email){

    }

    private void verificateUserPassword(String password){

    }
}
