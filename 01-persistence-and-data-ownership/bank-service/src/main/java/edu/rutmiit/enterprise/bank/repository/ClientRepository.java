package edu.rutmiit.enterprise.bank.repository;

import edu.rutmiit.enterprise.bank.domain.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByEmail(String email);
    Optional<ClientEntity> findByPassportNumber(String passportNumber);
}