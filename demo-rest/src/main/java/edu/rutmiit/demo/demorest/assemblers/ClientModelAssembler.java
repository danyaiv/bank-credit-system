package edu.rutmiit.demo.demorest.assemblers;

import edu.rutmiit.demo.bankapicontract.dto.ClientResponse;
import edu.rutmiit.demo.demorest.controllers.ClientController;
import edu.rutmiit.demo.demorest.domain.ClientEntity;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClientModelAssembler implements RepresentationModelAssembler<ClientEntity, ClientResponse> {

    @Override
    public ClientResponse toModel(ClientEntity entity) {
        ClientResponse response = new ClientResponse(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPassportNumber(),
                entity.getMonthlyIncome(),
                entity.getCurrentDebt(),
                entity.getCreatedAt()
        );

        response.add(linkTo(methodOn(ClientController.class).getClientById(entity.getId())).withSelfRel());
        response.add(linkTo(methodOn(ClientController.class).getAllClients()).withRel("collection"));

        return response;
    }
}