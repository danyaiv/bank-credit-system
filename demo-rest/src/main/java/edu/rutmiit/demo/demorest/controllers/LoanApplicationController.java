package edu.rutmiit.demo.demorest.controllers;

import edu.rutmiit.demo.bankapicontract.dto.LoanApplicationRequest;
import edu.rutmiit.demo.bankapicontract.dto.LoanApplicationResponse;
import edu.rutmiit.demo.bankapicontract.endpoints.LoanApplicationApi;
import edu.rutmiit.demo.demorest.assemblers.LoanApplicationModelAssembler;
import edu.rutmiit.demo.demorest.domain.LoanApplicationEntity;
import edu.rutmiit.demo.demorest.service.LoanApplicationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/loans")
public class LoanApplicationController implements LoanApplicationApi {

    private final LoanApplicationService loanService;
    private final LoanApplicationModelAssembler assembler;

    public LoanApplicationController(LoanApplicationService loanService, LoanApplicationModelAssembler assembler) {
        this.loanService = loanService;
        this.assembler = assembler;
    }

    @Override
    public ResponseEntity<LoanApplicationResponse> getLoanById(Long id) {
        LoanApplicationEntity loan = loanService.getLoanById(id);
        return ResponseEntity.ok(assembler.toModel(loan));
    }

    @Override
    public ResponseEntity<CollectionModel<LoanApplicationResponse>> getAllLoans() {
        List<LoanApplicationResponse> loans = loanService.getAllLoans().stream()
                .map(assembler::toModel)
                .toList();

        CollectionModel<LoanApplicationResponse> collection = CollectionModel.of(
                loans,
                linkTo(methodOn(LoanApplicationController.class).getAllLoans()).withSelfRel()
        );
        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<LoanApplicationResponse> createLoan(LoanApplicationRequest request) {
        LoanApplicationEntity created = loanService.createLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }
}