package edu.rutmiit.demo.bankapicontract.endpoints;

import edu.rutmiit.demo.bankapicontract.dto.ClientRequest;
import edu.rutmiit.demo.bankapicontract.dto.ClientResponse;
import edu.rutmiit.demo.bankapicontract.dto.PatchClientRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clients API", description = "Управление клиентами банка")
@RequestMapping("/api/clients")
public interface ClientApi {

    @Operation(summary = "Получить клиента по ID")
    @GetMapping("/{id}")
    ResponseEntity<ClientResponse> getClientById(@PathVariable("id") Long id);

    @Operation(summary = "Получить список всех клиентов")
    @GetMapping
    ResponseEntity<CollectionModel<ClientResponse>> getAllClients();

    @Operation(summary = "Зарегистрировать нового клиента")
    @PostMapping
    ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest request);

    @Operation(summary = "Частично обновить данные клиента (доход или задолженность)")
    @PatchMapping("/{id}")
    ResponseEntity<ClientResponse> patchClient(@PathVariable("id") Long id, @Valid @RequestBody PatchClientRequest request);

    @Operation(summary = "Удалить клиента из базы")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteClient(@PathVariable("id") Long id);
}