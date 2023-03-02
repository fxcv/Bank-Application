package me.springprojects.bankapplication.service;

import me.springprojects.bankapplication.entity.Authority;
import me.springprojects.bankapplication.entity.User;
import me.springprojects.bankapplication.entity.dto.UserDTO;
import me.springprojects.bankapplication.exceptions.InvalidInputDataException;
import me.springprojects.bankapplication.repository.AuthorityRepository;
import me.springprojects.bankapplication.repository.UserRepository;
import me.springprojects.bankapplication.service.verification.UserServiceVerification;
import me.springprojects.bankapplication.util.SeparatorUtil;
import me.springprojects.bankapplication.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class UserServiceTest {

    private final UserUtil userUtil = mock(UserUtil.class);
    private final MailService mailService = mock(MailService.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final AuthorityRepository authorityRepository = mock(AuthorityRepository.class);
    private final SeparatorUtil separator = new SeparatorUtil();
    private final UserServiceVerification userServiceVerification = new UserServiceVerification(userRepository);
    private final UserService userService = new UserService(userRepository, userUtil, separator, userServiceVerification, authorityRepository, mailService);

    @Test
    public void throwsExceptionWhenMissingInputData(){
        UserDTO userDTO = UserDTO.builder()
                .name(null)
                .lastname(null)
                .accountNumber(null)
                .email(null)
                .password(null)
                .balance(null)
                .build();

        assertThrows(InvalidInputDataException.class, () -> userService.createUser(userDTO));
    }

    @Test
    public void throwsExceptionWhenIncorrectInputData1(){
        UserDTO userDTO = UserDTO.builder()
                .name("incorrectname")
                .lastname("May")
                .accountNumber("1234 5678 9087")
                .email("email@gmail.com")
                .password("password")
                .build();

        assertThrows(InvalidInputDataException.class, () -> userService.createUser(userDTO));
    }

    @Test
    public void throwsExceptionWhenIncorrectInputData2(){
        UserDTO userDTO = UserDTO.builder()
                .name("Alice")
                .lastname("incorrectlastname")
                .accountNumber("1234 5678 9087")
                .email("email@gmail.com")
                .password("password")
                .build();

        assertThrows(InvalidInputDataException.class, () -> userService.createUser(userDTO));
    }

    @Test
    public void throwsExceptionWhenIncorrectInputData3(){
        UserDTO userDTO = UserDTO.builder()
                .name("Alice")
                .lastname("May")
                .accountNumber("incorrect account number")
                .email("email@gmail.com")
                .password("password")
                .build();

        assertThrows(InvalidInputDataException.class, () -> userService.createUser(userDTO));
    }

    @Test
    public void throwsExceptionWhenIncorrectInputData4(){
        UserDTO userDTO = UserDTO.builder()
                .name("Alice")
                .lastname("May")
                .accountNumber("1234 5678 9087")
                .email("incorrectemail")
                .password("password")
                .build();

        assertThrows(InvalidInputDataException.class, () -> userService.createUser(userDTO));
    }

    @Test
    public void throwsExceptionWhenIncorrectInputData5(){
        UserDTO userDTO = UserDTO.builder()
                .name("Alice")
                .lastname("May")
                .accountNumber("1234 5678 9087")
                .email("email@gmail.com")
                .password("badpass")
                .build();

        assertThrows(InvalidInputDataException.class, () -> userService.createUser(userDTO));
    }

    @Test
    public void successfullyCreatesAnUser(){
        UserDTO userDTO = UserDTO.builder()
                .name("Alice")
                .lastname("May")
                .accountNumber("1234 5678 9087")
                .email("email@gmail.com")
                .password("password")
                .build();
        Authority authority = Authority.builder()
                .name("TEST_NAME")
                .users(new ArrayList<>())
                .build();
        given(authorityRepository.findAuthorityByName(any())).willReturn(authority);

        ResponseEntity<UserDTO> res = userService.createUser(userDTO);

        verify(mailService, times(1)).sendAccountCreationMail(any());
        verify(userRepository, times(1)).save(any());
        verify(authorityRepository, times(1)).save(any());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }

    @Test
    public void successfullyFetchesAllUsers(){
        User user = User.builder()
                .name("Alice")
                .lastname("May")
                .accountNumber(separator.joinTheNumber("1234 5678 9087"))
                .email("email@gmail.com")
                .password("password")
                .balance(BigDecimal.ZERO)
                .locked(false)
                .authorities(new ArrayList<>())
                .cards(new ArrayList<>())
                .operations(new ArrayList<>())
                .build();
        given(userRepository.findAll()).willReturn(List.of(user));

        List<UserDTO> res = userService.getAllUsers();

        UserDTO resUser = res.get(0);
        verify(userRepository, times(1)).findAll();
        assertEquals(1, res.size());
        assertEquals(user.getName(), resUser.getName());
        assertEquals(user.getLastname(), resUser.getLastname());
        assertEquals("1234 5678 9087", resUser.getAccountNumber());
        assertEquals(user.getEmail(), resUser.getEmail());
        assertEquals(user.getPassword(), resUser.getPassword());
        assertEquals(BigDecimal.ZERO, resUser.getBalance());
    }
}