package me.springprojects.bankapplication.repository;

import me.springprojects.bankapplication.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    @Query("SELECT a FROM Authority a WHERE a.name = :name")
    public Authority findAuthorityByName(String name);
}
