package edu.rutmiit.enterprise.bank.repository;

import edu.rutmiit.enterprise.bank.domain.LoanApplicationEntity;
import edu.rutmiit.enterprise.bank.domain.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {
    List<LoanApplicationEntity> findByClientId(Long clientId);
    List<LoanApplicationEntity> findByStatus(LoanStatus status);
}