package edu.rutmiit.demo.demorest.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import edu.rutmiit.demo.bankapicontract.dto.ClientRequest;
import edu.rutmiit.demo.bankapicontract.dto.LoanApplicationRequest;
import edu.rutmiit.demo.demorest.domain.ClientEntity;
import edu.rutmiit.demo.demorest.domain.LoanApplicationEntity;
import edu.rutmiit.demo.demorest.service.ClientService;
import edu.rutmiit.demo.demorest.service.LoanApplicationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@DgsComponent
public class BankDataFetcher {

    private final ClientService clientService;
    private final LoanApplicationService loanService;

    public BankDataFetcher(ClientService clientService, LoanApplicationService loanService) {
        this.clientService = clientService;
        this.loanService = loanService;
    }

    @DgsQuery
    public ClientEntity client(@InputArgument Long id) {
        return clientService.getClientById(id);
    }

    @DgsQuery
    public List<ClientEntity> clients() {
        return clientService.getAllClients();
    }

    @DgsQuery
    public LoanApplicationEntity loan(@InputArgument Long id) {
        return loanService.getLoanById(id);
    }

    @DgsQuery
    public List<LoanApplicationEntity> loans() {
        return loanService.getAllLoans();
    }

    @DgsMutation
    public ClientEntity createClient(@InputArgument("input") Map<String, Object> input) {
        String name = input.containsKey("fullName") ? input.get("fullName").toString() : input.get("name").toString();
        ClientRequest request = new ClientRequest(
                name,
                input.get("email").toString(),
                input.get("passportNumber").toString(),
                new BigDecimal(input.get("monthlyIncome").toString()),
                new BigDecimal(input.get("currentDebt").toString())
        );
        return clientService.createClient(request);
    }

    @DgsMutation
    public LoanApplicationEntity submitLoanApplication(@InputArgument("input") Map<String, Object> input) {
        Long clientId = Long.parseLong(input.get("clientId").toString());
        BigDecimal amount = new BigDecimal(input.get("amount").toString());
        Integer termMonths = Integer.parseInt(input.get("termMonths").toString());
        String purpose = input.get("purpose").toString();

        LoanApplicationRequest request = new LoanApplicationRequest(
                clientId,
                amount,
                termMonths,
                purpose
        );
        return loanService.createLoan(request);
    }
}