package me.springprojects.bankapplication.controller;

import lombok.AllArgsConstructor;
import me.springprojects.bankapplication.entity.dto.DebitCardDTO;
import me.springprojects.bankapplication.service.DebitCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/cards")
@AllArgsConstructor
public class DebitCardController {

    private final DebitCardService debitCardService;

    @PostMapping("/add")
    public ResponseEntity<DebitCardDTO> addDebitCard(@RequestBody DebitCardDTO debitCardDTO){
        return debitCardService.addDebitCard(debitCardDTO);
    }
}
