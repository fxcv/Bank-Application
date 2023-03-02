package me.springprojects.bankapplication.service.verification;

import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.dto.UserDTO;
import me.springprojects.bankapplication.exceptions.InvalidInputDataException;
import me.springprojects.bankapplication.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class UserServiceVerification {
    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[A-Z][a-z]{2,10}$");
    private static final Pattern VALID_LASTNAME_PATTERN = Pattern.compile("^[A-Z][a-z]{2,15}$");
    private static final Pattern VALID_ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{4} [0-9]{4} [0-9]{4}$");
    private static final Pattern VALID_PASSWORD_PATTERN = Pattern.compile("^[0-9A-Za-z$@#.]{8,20}$");
    private static final Pattern VALID_EMAIL_PATTERN = Pattern.compile("^[A-Za-z]{4,10}@[A-Za-z.]{2,5}\\.[a-z]{2,4}$");

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
        Matcher match = VALID_NAME_PATTERN.matcher(name);
        if(!match.find()) throw new InvalidInputDataException("Please provide correct name.");
    }

    private void verificateUserLastname(String lastname){
        Matcher match = VALID_LASTNAME_PATTERN.matcher(lastname);
        if(!match.find()) throw new InvalidInputDataException("Please provide correct lastname.");
    }

    private void verificateUserAccountNumber(String accountNumber){
        Matcher match = VALID_ACCOUNT_NUMBER_PATTERN.matcher(accountNumber);
        if(!match.find()) throw new InvalidInputDataException("Please provide correct account number.");
    }

    private void verificateUserEmail(String email){
        Matcher match = VALID_EMAIL_PATTERN.matcher(email);
        if(!match.find()) throw new InvalidInputDataException("Please provide correct email.");
    }

    private void verificateUserPassword(String password){
        Matcher match = VALID_PASSWORD_PATTERN.matcher(password);
        if(!match.find()) throw new InvalidInputDataException("Please provide correct password.");
    }

    public void verificateUserBalance(BigDecimal balance, BigDecimal amount){
        if(amount.longValue() > balance.longValue()) throw new InvalidInputDataException("You do not have enough balance");
    }
}
