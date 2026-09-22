package edu.rutmiit.demo.demorest.service;

import edu.rutmiit.demo.bankapicontract.dto.LoanApplicationRequest;
import edu.rutmiit.demo.demorest.event.LoanEventPublisher;
import edu.rutmiit.demo.demorest.storage.InMemoryStorage;
import edu.rutmiit.demo.demorest.storage.LoanApplication;
import edu.rutmiit.demo.events.LoanEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LoanApplicationService {

    private final InMemoryStorage storage;
    private final ClientService clientService;
    private final LoanEventPublisher eventPublisher;

    public LoanApplicationService(InMemoryStorage storage,
                                  ClientService clientService,
                                  LoanEventPublisher eventPublisher) {
        this.storage = storage;
        this.clientService = clientService;
        this.eventPublisher = eventPublisher;
    }

    public LoanApplication getLoanById(Long id) {
        return storage.findLoanById(id)
                .orElseThrow(() -> new NoSuchElementException("Заявка с ID " + id + " не найдена"));
    }

    public List<LoanApplication> getAllLoans() {
        return storage.findAllLoans();
    }

    public LoanApplication createLoan(LoanApplicationRequest request) {
        // Проверяем, существует ли клиент
        clientService.getClientById(request.clientId());

        LoanApplication loan = new LoanApplication(
                null,
                request.clientId(),
                request.amount(),
                request.termMonths(),
                request.purpose()
        );
        LoanApplication saved = storage.saveLoan(loan);

        // Отправляем событие в RabbitMQ для запуска скоринга
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