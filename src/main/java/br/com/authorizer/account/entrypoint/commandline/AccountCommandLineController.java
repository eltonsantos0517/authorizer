package br.com.authorizer.account.entrypoint.commandline;

import authorizer_java.App;
import br.com.authorizer.account.usecase.*;

import java.util.Collections;
import java.util.List;

public class AccountCommandLineController {

    private CreateAccountUseCase createAccount;

    public AccountCommandLineController(CreateAccountUseCase createAccount) {
        this.createAccount = createAccount;
    }

    public CreateAccountResponse createAccount(App.Account account) {

        Account responseAccount;
        List<AccountViolation> violations = Collections.emptyList();

        try {
            responseAccount = createAccount.execute(CreateAccountRequest.Builder
                    .aRequest()
                    .withActiveCard(account.activeCard)
                    .withAvailableLimit(account.availableLimit)
                    .build()
            );
        } catch (AccountViolationException violationException) {
            responseAccount = violationException.getAccount();
            violations = violationException.getViolations();
        } catch (Exception e) {
            //TODO log
            e.printStackTrace();
            throw e;
        }

        return CreateAccountResponse.Builder
                .aResponse()
                .withActiveCard(responseAccount.isActiveCard())
                .withAvailableLimit(responseAccount.getAvailableLimit())
                .withViolations(violations)
                .build();
    }
}
