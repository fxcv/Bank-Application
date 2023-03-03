package me.springprojects.bankapplication.service;

import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.DebitCard;
import me.springprojects.bankapplication.entity.User;
import me.springprojects.bankapplication.entity.dto.DebitCardDTO;
import me.springprojects.bankapplication.repository.DebitCardRepository;
import me.springprojects.bankapplication.repository.UserRepository;
import me.springprojects.bankapplication.service.verification.DebitCardServiceVerification;
import me.springprojects.bankapplication.util.SeparatorUtil;
import me.springprojects.bankapplication.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DebitCardService {

    private final DebitCardRepository debitCardRepository;
    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final DebitCardServiceVerification debitCardServiceVerification;
    private final SeparatorUtil separator;

    public ResponseEntity<DebitCardDTO> addDebitCard(DebitCardDTO debitCardDTO){
        debitCardServiceVerification.verificateInputDebitCardData(debitCardDTO);

        User user = userUtil.getUserFromSecurityContext(); // should always return user
        DebitCard debitCard = DebitCard.builder()
                .debitCardNumber(separator.joinTheNumber(debitCardDTO.getDebitCardNumber()))
                .expDate(debitCardDTO.getExpDate())
                .cardOwner(user)
                .build();
        user.getCards().add(debitCard);

        userRepository.save(user);
        debitCardRepository.save(debitCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(debitCardDTO);
    }
}
