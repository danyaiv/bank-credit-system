package edu.rutmiit.enterprise.bank.service;

import edu.rutmiit.enterprise.bank.domain.ClientEntity;
import edu.rutmiit.enterprise.bank.domain.LoanApplicationEntity;
import edu.rutmiit.enterprise.bank.domain.LoanStatus;
import edu.rutmiit.enterprise.bank.repository.ClientRepository;
import edu.rutmiit.enterprise.bank.repository.LoanApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class BankService {

    private final ClientRepository clientRepository;
    private final LoanApplicationRepository loanRepository;

    public BankService(ClientRepository clientRepository, LoanApplicationRepository loanRepository) {
        this.clientRepository = clientRepository;
        this.loanRepository = loanRepository;
    }

    // клиенты

    public List<ClientEntity> getAllClients() {
        return clientRepository.findAll();
    }

    public ClientEntity getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Клиент с ID " + id + " не найден"));
    }

    @Transactional
    public ClientEntity createClient(String fullName, String email, String passportNumber,
                                     BigDecimal monthlyIncome, BigDecimal currentDebt) {
        if (clientRepository.findByPassportNumber(passportNumber).isPresent()) {
            throw new IllegalArgumentException("Клиент с паспортом " + passportNumber + " уже зарегистрирован");
        }
        ClientEntity client = new ClientEntity(fullName, email, passportNumber, monthlyIncome, currentDebt);
        return clientRepository.save(client);
    }

    // заявки

    public List<LoanApplicationEntity> getAllLoans() {
        return loanRepository.findAll();
    }

    public LoanApplicationEntity getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Заявка с ID " + id + " не найдена"));
    }

    public List<LoanApplicationEntity> getLoansByClientId(Long clientId) {
        return loanRepository.findByClientId(clientId);
    }

    @Transactional
    public LoanApplicationEntity createLoanApplication(Long clientId, BigDecimal amount, Integer termMonths, String purpose) {
        ClientEntity client = getClientById(clientId);

        // Создаем заявку в статусе PENDING (ожидает проверки скорингом)
        LoanApplicationEntity loan = new LoanApplicationEntity(client, amount, termMonths, purpose);
        loan.setStatus(LoanStatus.PENDING);
        loan.setInterestRate(null);
        loan.setDebtLoadRatio(null);
        loan.setRejectionReason(null);

        // Сохраняем в PostgreSQL через Hibernate / JPA
        return loanRepository.save(loan);
    }
}