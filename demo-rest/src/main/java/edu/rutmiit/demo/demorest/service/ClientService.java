package edu.rutmiit.demo.demorest.service;

import edu.rutmiit.demo.bankapicontract.dto.ClientRequest;
import edu.rutmiit.demo.bankapicontract.dto.PatchClientRequest;
import edu.rutmiit.demo.demorest.domain.ClientEntity;
import edu.rutmiit.demo.demorest.event.ClientEventPublisher;
import edu.rutmiit.demo.demorest.exception.ClientAlreadyExistsException;
import edu.rutmiit.demo.demorest.repository.ClientRepository;
import edu.rutmiit.demo.events.ClientEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true) // Чтение в read-only транзакции
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientEventPublisher eventPublisher;

    public ClientService(ClientRepository clientRepository, ClientEventPublisher eventPublisher) {
        this.clientRepository = clientRepository;
        this.eventPublisher = eventPublisher;
    }

    public ClientEntity getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Клиент с ID " + id + " не найден"));
    }

    public List<ClientEntity> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional // Создание в активной транзакции
    public ClientEntity createClient(ClientRequest request) {
        // Проверка уникального паспорта -> выброс 409 Conflict
        if (clientRepository.findByPassportNumber(request.passportNumber()).isPresent()) {
            throw new ClientAlreadyExistsException("Клиент с паспортом " + request.passportNumber() + " уже зарегистрирован");
        }

        ClientEntity client = new ClientEntity(
                request.name(),
                request.email(),
                request.passportNumber(),
                request.monthlyIncome(),
                request.currentDebt()
        );
        ClientEntity saved = clientRepository.save(client);

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

    @Transactional
    public ClientEntity patchClient(Long id, PatchClientRequest request) {
        ClientEntity client = getClientById(id);
        if (request.monthlyIncome() != null) {
            client.setMonthlyIncome(request.monthlyIncome());
        }
        if (request.currentDebt() != null) {
            client.setCurrentDebt(request.currentDebt());
        }
        return clientRepository.save(client);
    }

    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new NoSuchElementException("Клиент с ID " + id + " не найден");
        }
        clientRepository.deleteById(id);
    }
}