package me.springprojects.bankapplication.repository;

import me.springprojects.bankapplication.entity.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DebitCardRepository extends JpaRepository<DebitCard, String> {

    @Query("SELECT d FROM DebitCard d WHERE d.debitCardNumber = :number")
    public Optional<DebitCard> getDebitCardByNumber(long number);
}
