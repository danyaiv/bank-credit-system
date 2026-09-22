package edu.rutmiit.demo.demorest.storage;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryStorage {

    private final Map<Long, Client> clients = new ConcurrentHashMap<>();
    private final Map<Long, LoanApplication> loans = new ConcurrentHashMap<>();

    private final AtomicLong clientSequence = new AtomicLong(0);
    private final AtomicLong loanSequence = new AtomicLong(0);

    public InMemoryStorage() {
        // Клиент 1: низкий долг (10 000 руб) -> кредит одобрят
        saveClient(new Client(null, "Иванов Иван Иванович", "ivanov@bank.ru", "4510 123456",
                BigDecimal.valueOf(100000), BigDecimal.valueOf(10000)));

        // Клиент 2: высокий долг (60 000 руб) -> отказ по правилу 50%
        saveClient(new Client(null, "Петров Петр Петрович", "petrov@bank.ru", "4512 654321",
                BigDecimal.valueOf(80000), BigDecimal.valueOf(60000)));
    }

    public Client saveClient(Client client) {
        if (client.getId() == null) {
            client.setId(clientSequence.incrementAndGet());
        }
        clients.put(client.getId(), client);
        return client;
    }

    public Optional<Client> findClientById(Long id) {
        return Optional.ofNullable(clients.get(id));
    }

    public List<Client> findAllClients() {
        return new ArrayList<>(clients.values());
    }

    public boolean deleteClient(Long id) {
        return clients.remove(id) != null;
    }

    public LoanApplication saveLoan(LoanApplication loan) {
        if (loan.getId() == null) {
            loan.setId(loanSequence.incrementAndGet());
        }
        loans.put(loan.getId(), loan);
        return loan;
    }

    public Optional<LoanApplication> findLoanById(Long id) {
        return Optional.ofNullable(loans.get(id));
    }

    public List<LoanApplication> findAllLoans() {
        return new ArrayList<>(loans.values());
    }
}