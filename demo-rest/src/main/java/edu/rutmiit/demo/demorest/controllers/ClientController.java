package edu.rutmiit.demo.demorest.controllers;

import edu.rutmiit.demo.bankapicontract.dto.ClientRequest;
import edu.rutmiit.demo.bankapicontract.dto.ClientResponse;
import edu.rutmiit.demo.bankapicontract.dto.PatchClientRequest;
import edu.rutmiit.demo.bankapicontract.endpoints.ClientApi;
import edu.rutmiit.demo.demorest.assemblers.ClientModelAssembler;
import edu.rutmiit.demo.demorest.service.ClientService;
import edu.rutmiit.demo.demorest.storage.Client;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController implements ClientApi {

    private final ClientService clientService;
    private final ClientModelAssembler assembler;

    public ClientController(ClientService clientService, ClientModelAssembler assembler) {
        this.clientService = clientService;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<ClientResponse> getClientById(Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(assembler.toModel(client));
    }

    @Override
    public ResponseEntity<CollectionModel<ClientResponse>> getAllClients() {
        List<ClientResponse> clients = clientService.getAllClients().stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<ClientResponse> collection = CollectionModel.of(
                clients,
                linkTo(methodOn(ClientController.class).getAllClients()).withSelfRel()
        );
        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<ClientResponse> createClient(ClientRequest request) {
        Client created = clientService.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @Override
    public ResponseEntity<ClientResponse> patchClient(Long id, PatchClientRequest request) {
        Client patched = clientService.patchClient(id, request);
        return ResponseEntity.ok(assembler.toModel(patched));
    }

    @Override
    public ResponseEntity<Void> deleteClient(Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}