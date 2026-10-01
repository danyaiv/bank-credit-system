package edu.rutmiit.demo.demorest.assemblers;

import edu.rutmiit.demo.bankapicontract.dto.LoanApplicationResponse;
import edu.rutmiit.demo.demorest.controllers.ClientController;
import edu.rutmiit.demo.demorest.controllers.LoanApplicationController;
import edu.rutmiit.demo.demorest.domain.LoanApplicationEntity;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class LoanApplicationModelAssembler implements RepresentationModelAssembler<LoanApplicationEntity, LoanApplicationResponse> {

    @Override
    public LoanApplicationResponse toModel(LoanApplicationEntity entity) {
        LoanApplicationResponse response = new LoanApplicationResponse(
                entity.getId(),
                entity.getClientId(),
                entity.getAmount(),
                entity.getTermMonths(),
                entity.getPurpose(),
                entity.getStatus() != null ? entity.getStatus().name() : "PENDING",
                entity.getInterestRate(),
                entity.getRejectionReason(),
                entity.getCreatedAt()
        );

        response.add(linkTo(methodOn(LoanApplicationController.class).getLoanById(entity.getId())).withSelfRel());
        response.add(linkTo(methodOn(LoanApplicationController.class).getAllLoans()).withRel("collection"));
        response.add(linkTo(methodOn(ClientController.class).getClientById(entity.getClientId())).withRel("client"));

        return response;
    }
}