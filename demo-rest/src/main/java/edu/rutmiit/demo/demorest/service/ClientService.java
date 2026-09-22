package edu.rutmiit.demo.demorest.service;

import edu.rutmiit.demo.bankapicontract.dto.ClientRequest;
import edu.rutmiit.demo.bankapicontract.dto.PatchClientRequest;
import edu.rutmiit.demo.demorest.event.ClientEventPublisher;
import edu.rutmiit.demo.demorest.storage.Client;
import edu.rutmiit.demo.demorest.storage.InMemoryStorage;
import edu.rutmiit.demo.events.ClientEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {

    private final InMemoryStorage storage;
    private final ClientEventPublisher eventPublisher;

    public ClientService(InMemoryStorage storage, ClientEventPublisher eventPublisher) {
        this.storage = storage;
        this.eventPublisher = eventPublisher;
    }

    public Client getClientById(Long id) {
        return storage.findClientById(id)
                .orElseThrow(() -> new NoSuchElementException("Клиент с ID " + id + " не найден"));
    }

    public List<Client> getAllClients() {
        return storage.findAllClients();
    }

    public Client createClient(ClientRequest request) {
        Client client = new Client(
                null,
                request.name(),
                request.email(),
                request.passportNumber(),
                request.monthlyIncome(),
                request.currentDebt()
        );
        Client saved = storage.saveClient(client);

        // Отправляем событие о регистрации в RabbitMQ
        eventPublisher.publishClientCreated(new ClientEvent.Created(
                saved.getId(),
                saved.getFullName(),
                saved.getEmail(),
                saved.getPassportNumber(),
                saved.getMonthlyIncome(),
                saved.getCurrentDebt(),
                saved.getCreatedAt()
        ));

        return saved;
    }

    public Client patchClient(Long id, PatchClientRequest request) {
        Client client = getClientById(id);
        if (request.monthlyIncome() != null) {
            client.setMonthlyIncome(request.monthlyIncome());
        }
        if (request.currentDebt() != null) {
            client.setCurrentDebt(request.currentDebt());
        }
        return storage.saveClient(client);
    }

    public void deleteClient(Long id) {
        if (!storage.deleteClient(id)) {
            throw new NoSuchElementException("Клиент с ID " + id + " не найден");
        }
    }
}