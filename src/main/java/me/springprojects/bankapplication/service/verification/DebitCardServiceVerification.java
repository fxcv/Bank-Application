package me.springprojects.bankapplication.service.verification;

import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.DebitCard;
import me.springprojects.bankapplication.entity.dto.DebitCardDTO;
import me.springprojects.bankapplication.exceptions.InvalidInputDataException;
import me.springprojects.bankapplication.repository.DebitCardRepository;
import me.springprojects.bankapplication.service.enums.DebitExceptions;
import me.springprojects.bankapplication.util.SeparatorUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class DebitCardServiceVerification {

    private static final Pattern VALID_DEBIT_CARD_NUMBER_PATTERN = Pattern.compile("^[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}$");
    public static final int CARD_EXPIRATION_YEARS = 5;
    private final DebitCardRepository debitCardRepository;
    private final SeparatorUtil separator;

    public void verificateInputDebitCardData(DebitCardDTO debitCardDTO){
        if(debitCardDTO.getDebitCardNumber() == null || debitCardDTO.getExpDate() == null) throw new InvalidInputDataException(DebitExceptions.NOT_ENOUGH_DATA);

        verificateCardNumber(debitCardDTO.getDebitCardNumber());
        verificateIfCardAlreadyExists(debitCardDTO.getDebitCardNumber());
        verificateExpDate(debitCardDTO.getExpDate());
    }

    private void verificateIfCardAlreadyExists(String debitCardNumber){
        Optional<DebitCard> debitCard = debitCardRepository.getDebitCardByNumber(separator.joinTheNumber(debitCardNumber));
        if(debitCard.isPresent()) throw new InvalidInputDataException(DebitExceptions.DEBIT_CARD_EXISTS);
    }

    private void verificateExpDate(LocalDate date){
        LocalDate now = LocalDate.now();
        if(!now.plusYears(CARD_EXPIRATION_YEARS).isEqual(date)) throw new InvalidInputDataException(DebitExceptions.INCORRECT_EXPIRATION_DATE);
    }

    private void verificateCardNumber(String debitCardNumber){
        Matcher match = VALID_DEBIT_CARD_NUMBER_PATTERN.matcher(debitCardNumber);
        if(!match.find()) throw new InvalidInputDataException(DebitExceptions.INCORRECT_DEBIT_CARD_NUMBER);
    }
}
