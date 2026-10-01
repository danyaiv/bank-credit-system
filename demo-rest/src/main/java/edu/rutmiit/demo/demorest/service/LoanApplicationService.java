package edu.rutmiit.demo.demorest.service;

import edu.rutmiit.demo.bankapicontract.dto.LoanApplicationRequest;
import edu.rutmiit.demo.demorest.domain.LoanApplicationEntity;
import edu.rutmiit.demo.demorest.domain.LoanStatus;
import edu.rutmiit.demo.demorest.event.LoanEventPublisher;
import edu.rutmiit.demo.demorest.repository.LoanApplicationRepository;
import edu.rutmiit.demo.events.LoanEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class LoanApplicationService {

    private final LoanApplicationRepository loanRepository;
    private final ClientService clientService;
    private final LoanEventPublisher eventPublisher;

    public LoanApplicationService(LoanApplicationRepository loanRepository,
                                  ClientService clientService,
                                  LoanEventPublisher eventPublisher) {
        this.loanRepository = loanRepository;
        this.clientService = clientService;
        this.eventPublisher = eventPublisher;
    }

    public LoanApplicationEntity getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Заявка с ID " + id + " не найдена"));
    }

    public List<LoanApplicationEntity> getAllLoans() {
        return loanRepository.findAll();
    }

    @Transactional
    public LoanApplicationEntity createLoan(LoanApplicationRequest request) {
        clientService.getClientById(request.clientId());

        LoanApplicationEntity loan = new LoanApplicationEntity(
                request.clientId(),
                request.amount(),
                request.termMonths(),
                request.purpose()
        );
        loan.setStatus(LoanStatus.PENDING);

        LoanApplicationEntity saved = loanRepository.save(loan);

        eventPublisher.publishLoanCreated(new LoanEvent.Created(
                saved.getId(),
                saved.getClientId(),
                saved.getAmount(),
                saved.getTermMonths(),
                saved.getPurpose(),
                saved.getCreatedAt()
        ));

        return saved;
    }
}