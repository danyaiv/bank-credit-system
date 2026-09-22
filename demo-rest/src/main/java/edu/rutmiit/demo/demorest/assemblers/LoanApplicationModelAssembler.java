package edu.rutmiit.demo.demorest.assemblers;

import edu.rutmiit.demo.bankapicontract.dto.LoanApplicationResponse;
import edu.rutmiit.demo.demorest.controllers.ClientController;
import edu.rutmiit.demo.demorest.controllers.LoanApplicationController;
import edu.rutmiit.demo.demorest.storage.LoanApplication;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class LoanApplicationModelAssembler implements RepresentationModelAssembler<LoanApplication, LoanApplicationResponse> {

    @Override
    public LoanApplicationResponse toModel(LoanApplication entity) {
        LoanApplicationResponse response = new LoanApplicationResponse(
                entity.getId(),
                entity.getClientId(),
                entity.getAmount(),
                entity.getTermMonths(),
                entity.getPurpose(),
                entity.getStatus(),
                entity.getInterestRate(),
                entity.getRejectionReason(),
                entity.getCreatedAt()
        );

        // HATEOAS ссылки на саму заявку, на коллекцию и на клиента-заявителя
        response.add(linkTo(methodOn(LoanApplicationController.class).getLoanById(entity.getId())).withSelfRel());
        response.add(linkTo(methodOn(LoanApplicationController.class).getAllLoans()).withRel("collection"));
        response.add(linkTo(methodOn(ClientController.class).getClientById(entity.getClientId())).withRel("client"));

        return response;
    }
}