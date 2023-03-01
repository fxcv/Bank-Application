package me.springprojects.bankapplication.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.Authority;
import me.springprojects.bankapplication.entity.User;
import me.springprojects.bankapplication.entity.dto.DebitCardDTO;
import me.springprojects.bankapplication.entity.dto.UserDTO;
import me.springprojects.bankapplication.repository.AuthorityRepository;
import me.springprojects.bankapplication.repository.UserRepository;
import me.springprojects.bankapplication.service.verification.UserServiceVerification;
import me.springprojects.bankapplication.util.SeparatorUtil;
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
    private final SeparatorUtil separator;
    private final UserServiceVerification userServiceVerification;
    private final AuthorityRepository authorityRepository;

    @Transactional(rollbackOn = RuntimeException.class)
    public ResponseEntity<UserDTO> createUser(UserDTO userDTO){
        //todo verification

        User user = User.builder()
                .name(userDTO.getName())
                .lastname(userDTO.getLastname())
                .accountNumber(separator.joinTheNumber(userDTO.getAccountNumber()))
                .locked(false)
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .balance(BigDecimal.ZERO)
                .operations(new ArrayList<>())
                .cards(new ArrayList<>())
                .authorities(new ArrayList<>())
                .build();
        Authority userAuthority = authorityRepository.findAuthorityByName("ROLE_USER"); // should always exist in the database
        user.getAuthorities().add(userAuthority);
        userAuthority.getUsers().add(user);

        userRepository.save(user);
        authorityRepository.save(userAuthority);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream()
                                       .map(user -> {
                                           UserDTO userDTO = UserDTO.builder()
                                                   .name(user.getName())
                                                   .lastname(user.getLastname())
                                                   .accountNumber(separator.separateTheNumberPerFour(user.getAccountNumber()))
                                                   .email(user.getEmail())
                                                   .password(user.getPassword())
                                                   .balance(user.getBalance())
                                                   .build();
                                           return userDTO;
                                       })
                                       .collect(Collectors.toList());
    }
}
