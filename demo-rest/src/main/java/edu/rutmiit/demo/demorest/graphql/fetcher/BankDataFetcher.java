package edu.rutmiit.demo.demorest.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import edu.rutmiit.demo.bankapicontract.dto.ClientRequest;
import edu.rutmiit.demo.bankapicontract.dto.LoanApplicationRequest;
import edu.rutmiit.demo.demorest.service.ClientService;
import edu.rutmiit.demo.demorest.service.LoanApplicationService;
import edu.rutmiit.demo.demorest.storage.Client;
import edu.rutmiit.demo.demorest.storage.LoanApplication;

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

    // --- QUERIES (ЧТЕНИЕ) ---

    @DgsQuery
    public Client client(@InputArgument Long id) {
        return clientService.getClientById(id);
    }

    @DgsQuery
    public List<Client> clients() {
        return clientService.getAllClients();
    }

    @DgsQuery
    public LoanApplication loan(@InputArgument Long id) {
        return loanService.getLoanById(id);
    }

    @DgsQuery
    public List<LoanApplication> loans() {
        return loanService.getAllLoans();
    }

    // --- MUTATIONS (ИЗМЕНЕНИЕ ДАННЫХ) ---

    @DgsMutation
    public Client createClient(@InputArgument("input") Map<String, Object> input) {
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
    public LoanApplication submitLoanApplication(@InputArgument("input") Map<String, Object> input) {
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