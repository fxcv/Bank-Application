package me.springprojects.bankapplication.service;

import me.springprojects.bankapplication.entity.DebitCard;
import me.springprojects.bankapplication.entity.User;
import me.springprojects.bankapplication.entity.dto.DebitCardDTO;
import me.springprojects.bankapplication.exceptions.InvalidInputDataException;
import me.springprojects.bankapplication.repository.DebitCardRepository;
import me.springprojects.bankapplication.repository.UserRepository;
import me.springprojects.bankapplication.service.verification.DebitCardServiceVerification;
import me.springprojects.bankapplication.util.SeparatorUtil;
import me.springprojects.bankapplication.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class DebitCardServiceTest {

    private final DebitCardRepository debitCardRepository = mock(DebitCardRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserUtil userUtil = mock(UserUtil.class);
    private final SeparatorUtil separator = new SeparatorUtil();
    private final DebitCardServiceVerification debitCardServiceVerification = new DebitCardServiceVerification(debitCardRepository, separator);
    private final DebitCardService debitCardService = new DebitCardService(debitCardRepository, userRepository, userUtil, debitCardServiceVerification, separator);


    @Test
    public void throwsExceptionIfNoCardNumberProvided(){
        DebitCardDTO debitCardDTO = DebitCardDTO.builder()
                .debitCardNumber(null)
                .expDate(LocalDate.now().plusYears(5))
                .build();

        assertThrows(InvalidInputDataException.class, () -> debitCardService.addDebitCard(debitCardDTO));
    }

    @Test
    public void throwsExceptionIfNoExpDateProvided(){
        DebitCardDTO debitCardDTO = DebitCardDTO.builder()
                .debitCardNumber("3432 3432 3432 3432")
                .expDate(null)
                .build();

        assertThrows(InvalidInputDataException.class, () -> debitCardService.addDebitCard(debitCardDTO));
    }

    @Test
    public void throwsExceptionIfIncorrectCardNumber(){
        DebitCardDTO debitCardDTO = DebitCardDTO.builder()
                .debitCardNumber("")
                .expDate(LocalDate.now().plusYears(5))
                .build();

        assertThrows(InvalidInputDataException.class, () -> debitCardService.addDebitCard(debitCardDTO));
    }

    @Test
    public void throwsExceptionIfIncorrectExpDate(){
        DebitCardDTO debitCardDTO = DebitCardDTO.builder()
                .debitCardNumber("3432 3432 3432 3432")
                .expDate(LocalDate.now())
                .build();

        assertThrows(InvalidInputDataException.class, () -> debitCardService.addDebitCard(debitCardDTO));
    }

    @Test
    public void throwsExceptionIfCardAlreadyExists(){
        DebitCardDTO debitCardDTO = DebitCardDTO.builder()
                .debitCardNumber("3432 3432 3432 3432")
                .expDate(LocalDate.now().plusYears(5))
                .build();
        given(debitCardRepository.getDebitCardByNumber(anyLong())).willReturn(Optional.of(new DebitCard()));

        assertThrows(InvalidInputDataException.class, () -> debitCardService.addDebitCard(debitCardDTO));
    }

    @Test
    public void successfullyAddsNewCard(){
        DebitCardDTO debitCardDTO = DebitCardDTO.builder()
                .debitCardNumber("3432 3432 3432 3432")
                .expDate(LocalDate.now().plusYears(5))
                .build();
        User user = User.builder()
                .cards(new ArrayList<>())
                .build();
        given(userUtil.getUserFromSecurityContext()).willReturn(user);

        ResponseEntity<DebitCardDTO> res = debitCardService.addDebitCard(debitCardDTO);

        verify(userRepository, times(1)).save(any());
        verify(debitCardRepository, times(1)).save(any());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        assertEquals(1, user.getCards().size());
    }
}