package me.springprojects.bankapplication.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.Authority;
import me.springprojects.bankapplication.entity.User;
import me.springprojects.bankapplication.entity.dto.UserDTO;
import me.springprojects.bankapplication.exceptions.InvalidInputDataException;
import me.springprojects.bankapplication.repository.AuthorityRepository;
import me.springprojects.bankapplication.repository.UserRepository;
import me.springprojects.bankapplication.service.verification.UserServiceVerification;
import me.springprojects.bankapplication.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final UserServiceVerification userServiceVerification;
    private final AuthorityRepository authorityRepository;
    private final MailService mailService;

    @Transactional(rollbackOn = RuntimeException.class)
    public ResponseEntity<UserDTO> registerUser(UserDTO userDTO){
        userServiceVerification.verificateInputUserData(userDTO);

        User user = User.builder()
                .name(userDTO.getName())
                .lastname(userDTO.getLastname())
                .accountNumber(userDTO.getAccountNumber())
                .locked(false)
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .balance(BigDecimal.ZERO)
                .authorities(new ArrayList<>())
                .build();
        Authority userAuthority = authorityRepository.findAuthorityByName("ROLE_USER"); // should always exist in the database
        user.getAuthorities().add(userAuthority);
        userAuthority.getUsers().add(user);

        userRepository.save(user);
        authorityRepository.save(userAuthority);
        mailService.sendAccountCreationMail(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream()
                                       .map(user -> {
                                           UserDTO userDTO = UserDTO.builder()
                                                   .name(user.getName())
                                                   .lastname(user.getLastname())
                                                   .accountNumber(user.getAccountNumber())
                                                   .email(user.getEmail())
                                                   .password(user.getPassword())
                                                   .balance(user.getBalance())
                                                   .build();
                                           return userDTO;
                                       })
                                       .collect(Collectors.toList());
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public void depositMoney(BigDecimal amount){
        User loggedUser = userUtil.getUserFromSecurityContext(); // should return logged-in user
        synchronized(this){
            loggedUser.setBalance(loggedUser.getBalance().add(amount));
        }
        userRepository.save(loggedUser);
        mailService.sendDepositMail(loggedUser.getEmail());
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public void withdrawMoney(BigDecimal amount){
        User loggedUser = userUtil.getUserFromSecurityContext(); // should return logged-in user
        userServiceVerification.verificateUserBalance(loggedUser.getBalance(), amount);
        synchronized (this){
            loggedUser.setBalance(loggedUser.getBalance().subtract(amount));
        }
        userRepository.save(loggedUser);
        mailService.sendWithdrawalMail(loggedUser.getEmail());
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public void transferMoney(BigDecimal amount, String receiverId){
        User loggedUser = userUtil.getUserFromSecurityContext(); // should return logged-in user
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new InvalidInputDataException("Receiver not found."));
        userServiceVerification.verificateUserBalance(loggedUser.getBalance(), amount);
        synchronized (this){
            loggedUser.setBalance(loggedUser.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }
        userRepository.save(loggedUser);
        userRepository.save(receiver);
        mailService.sendTransferMail(loggedUser.getEmail());
    }
}
