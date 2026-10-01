package edu.rutmiit.demo.demorest.repository;

import edu.rutmiit.demo.demorest.domain.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {
    List<LoanApplicationEntity> findByClientId(Long clientId);
}