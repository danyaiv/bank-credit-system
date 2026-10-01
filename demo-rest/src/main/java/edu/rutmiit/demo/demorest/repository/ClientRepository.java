package edu.rutmiit.demo.demorest.repository;

import edu.rutmiit.demo.demorest.domain.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByPassportNumber(String passportNumber);
}