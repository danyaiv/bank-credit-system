package edu.rutmiit.enterprise.bank.api;

import edu.rutmiit.enterprise.bank.domain.ClientEntity;
import edu.rutmiit.enterprise.bank.domain.LoanApplicationEntity;
import edu.rutmiit.enterprise.bank.service.BankService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    // клиенты

    @GetMapping("/clients")
    public List<ClientResponse> getAllClients() {
        return bankService.getAllClients().stream()
                .map(this::toClientResponse)
                .toList();
    }

    @GetMapping("/clients/{id}")
    public ClientResponse getClientById(@PathVariable Long id) {
        return toClientResponse(bankService.getClientById(id));
    }

    @PostMapping("/clients")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponse createClient(@Valid @RequestBody CreateClientRequest request) {
        ClientEntity created = bankService.createClient(
                request.fullName(),
                request.email(),
                request.passportNumber(),
                request.monthlyIncome(),
                request.currentDebt()
        );
        return toClientResponse(created);
    }

    // заявки

    @GetMapping("/loans")
    public List<LoanResponse> getAllLoans() {
        return bankService.getAllLoans().stream()
                .map(this::toLoanResponse)
                .toList();
    }

    @GetMapping("/loans/{id}")
    public LoanResponse getLoanById(@PathVariable Long id) {
        return toLoanResponse(bankService.getLoanById(id));
    }

    @GetMapping("/clients/{clientId}/loans")
    public List<LoanResponse> getLoansByClientId(@PathVariable Long clientId) {
        return bankService.getLoansByClientId(clientId).stream()
                .map(this::toLoanResponse)
                .toList();
    }

    @PostMapping("/loans")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse createLoan(@Valid @RequestBody CreateLoanRequest request) {
        LoanApplicationEntity created = bankService.createLoanApplication(
                request.clientId(),
                request.amount(),
                request.termMonths(),
                request.purpose()
        );
        return toLoanResponse(created);
    }

    // маппинг в дто

    private ClientResponse toClientResponse(ClientEntity entity) {
        return new ClientResponse(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPassportNumber(),
                entity.getMonthlyIncome(),
                entity.getCurrentDebt(),
                entity.getCreatedAt()
        );
    }

    private LoanResponse toLoanResponse(LoanApplicationEntity entity) {
        return new LoanResponse(
                entity.getId(),
                entity.getClient().getId(),
                entity.getAmount(),
                entity.getTermMonths(),
                entity.getPurpose(),
                entity.getStatus().name(),
                entity.getInterestRate(),
                entity.getDebtLoadRatio(),
                entity.getRejectionReason(),
                entity.getCreatedAt()
        );
    }
}