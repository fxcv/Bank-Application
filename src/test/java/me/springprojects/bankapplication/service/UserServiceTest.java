package me.springprojects.bankapplication.service;

import me.springprojects.bankapplication.entity.Authority;
import me.springprojects.bankapplication.entity.User;
import me.springprojects.bankapplication.entity.dto.UserDTO;
import me.springprojects.bankapplication.exceptions.InvalidInputDataException;
import me.springprojects.bankapplication.repository.AuthorityRepository;
import me.springprojects.bankapplication.repository.UserRepository;
import me.springprojects.bankapplication.service.verification.UserServiceVerification;
import me.springprojects.bankapplication.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class UserServiceTest {

    private final UserUtil userUtil = mock(UserUtil.class);
    private final MailService mailService = mock(MailService.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final AuthorityRepository authorityRepository = mock(AuthorityRepository.class);
    private final UserServiceVerification userServiceVerification = new UserServiceVerification(userRepository);
    private final UserService userService = new UserService(userRepository, userUtil, userServiceVerification, authorityRepository, mailService);
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

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

        assertThrows(InvalidInputDataException.class, () -> userService.registerUser(userDTO));
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

        assertThrows(InvalidInputDataException.class, () -> userService.registerUser(userDTO));
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

        assertThrows(InvalidInputDataException.class, () -> userService.registerUser(userDTO));
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

        assertThrows(InvalidInputDataException.class, () -> userService.registerUser(userDTO));
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

        assertThrows(InvalidInputDataException.class, () -> userService.registerUser(userDTO));
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

        assertThrows(InvalidInputDataException.class, () -> userService.registerUser(userDTO));
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

        ResponseEntity<UserDTO> res = userService.registerUser(userDTO);

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
                .accountNumber("1234 5678 9087")
                .email("email@gmail.com")
                .password("password")
                .balance(BigDecimal.ZERO)
                .locked(false)
                .authorities(new ArrayList<>())
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

    @Test
    public void successfullyDepositsMoney(){
        User user = User.builder()
                .name("Alice")
                .lastname("May")
                .balance(BigDecimal.ZERO)
                .build();
        given(userUtil.getUserFromSecurityContext()).willReturn(user);

        userService.depositMoney(BigDecimal.TEN);

        verify(userRepository, times(1)).save(any());
        verify(mailService, times(1)).sendDepositMail(any());
        assertEquals(BigDecimal.TEN, user.getBalance());
    }

    @Test
    public void successfullyWithdrawsMoney(){
        User user = User.builder()
                .name("Alice")
                .lastname("May")
                .balance(BigDecimal.TEN)
                .build();
        given(userUtil.getUserFromSecurityContext()).willReturn(user);

        userService.withdrawMoney(BigDecimal.TEN);

        verify(userRepository, times(1)).save(any());
        verify(mailService, times(1)).sendWithdrawalMail(any());
        assertEquals(BigDecimal.ZERO, user.getBalance());
    }

    @Test
    public void successfullyTransfersMoney(){
        User transferer = User.builder()
                .name("Alice")
                .lastname("May")
                .balance(BigDecimal.TEN)
                .build();
        User receiver = User.builder()
                .name("Tom")
                .lastname("May")
                .balance(BigDecimal.TEN)
                .build();
        given(userUtil.getUserFromSecurityContext()).willReturn(transferer);
        given(userRepository.findById(any())).willReturn(Optional.of(receiver));

        userService.transferMoney(BigDecimal.TEN, "");

        verify(userRepository, times(2)).save(any());
        verify(mailService, times(1)).sendTransferMail(any());
        assertEquals(BigDecimal.ZERO, transferer.getBalance());
        assertEquals(BigDecimal.valueOf(20), receiver.getBalance());
    }

    @Test
    public void throwsExceptionIfReceiverNotFound(){
        User transferer = User.builder()
                .name("Alice")
                .lastname("May")
                .balance(BigDecimal.TEN)
                .build();
        given(userUtil.getUserFromSecurityContext()).willReturn(transferer);
        given(userRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(InvalidInputDataException.class, () -> userService.transferMoney(BigDecimal.TEN, ""));
    }

    @Test
    public void throwsExceptionIfUserHasNotEnoughMoney(){
        User user = User.builder()
                .name("Alice")
                .lastname("May")
                .balance(BigDecimal.TEN)
                .build();
        given(userUtil.getUserFromSecurityContext()).willReturn(user);
        given(userRepository.findById(any())).willReturn(Optional.of(new User()));

        assertThrows(InvalidInputDataException.class, () -> userService.withdrawMoney(BigDecimal.valueOf(1000)));
        assertThrows(InvalidInputDataException.class, () -> userService.transferMoney(BigDecimal.valueOf(1000), ""));
    }

    @Test
    public void checkIfMultiThreadingWorksCorrectly() throws InterruptedException {
        User user = new User();
        user.setBalance(BigDecimal.ZERO);
        given(userUtil.getUserFromSecurityContext()).willReturn(user);

        IntStream.range(0, 1000).forEach(num -> {
            executorService.submit(() -> {
                userService.depositMoney(BigDecimal.ONE);
            });
        });
        Thread.sleep(1000);

        assertEquals(BigDecimal.valueOf(1000), user.getBalance());
    }

    @Test
    public void checkIfMultiThreadingWorksCorrectly2() throws InterruptedException {
        User user = new User();
        user.setBalance(BigDecimal.valueOf(1000));
        given(userUtil.getUserFromSecurityContext()).willReturn(user);

        IntStream.range(0, 1000).forEach(num -> {
            executorService.submit(() -> {
                userService.withdrawMoney((BigDecimal.ONE));
            });
        });
        Thread.sleep(1000);

        assertEquals(BigDecimal.valueOf(0), user.getBalance());
    }

    @Test
    public void checkIfMultiThreadingWorksCorrectly3() throws InterruptedException {
        User user = new User();
        user.setBalance(BigDecimal.valueOf(1000));
        User receiver = new User();
        receiver.setBalance(BigDecimal.valueOf(5000));
        given(userUtil.getUserFromSecurityContext()).willReturn(user);
        given(userRepository.findById(any())).willReturn(Optional.of(receiver));

        IntStream.range(0, 1000).forEach(num -> {
            executorService.submit(() -> {
                userService.transferMoney(BigDecimal.ONE, "");
            });
        });
        Thread.sleep(1000);

        assertEquals(BigDecimal.valueOf(0), user.getBalance());
        assertEquals(BigDecimal.valueOf(6000), receiver.getBalance());
    }
}