package edu.rutmiit.demo.bankapicontract.endpoints;

import edu.rutmiit.demo.bankapicontract.dto.LoanApplicationRequest;
import edu.rutmiit.demo.bankapicontract.dto.LoanApplicationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Loan Applications API", description = "Управление кредитными заявками")
@RequestMapping("/api/loans")
public interface LoanApplicationApi {

    @Operation(summary = "Получить кредитную заявку по ID")
    @GetMapping("/{id}")
    ResponseEntity<LoanApplicationResponse> getLoanById(@PathVariable("id") Long id);

    @Operation(summary = "Получить все кредитные заявки")
    @GetMapping
    ResponseEntity<CollectionModel<LoanApplicationResponse>> getAllLoans();

    @Operation(summary = "Подать новую заявку на кредит")
    @PostMapping
    ResponseEntity<LoanApplicationResponse> createLoan(@Valid @RequestBody LoanApplicationRequest request);
}