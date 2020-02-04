package br.com.authorizer.account.entrypoint.commandline;

import authorizer_java.App;
import br.com.authorizer.Violation;
import br.com.authorizer.ViolationException;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.CreateAccountRequest;
import br.com.authorizer.account.usecase.CreateAccountUseCase;

import java.util.Collections;
import java.util.List;

public class AccountCommandLineController {

    private CreateAccountUseCase createAccount;

    public AccountCommandLineController(CreateAccountUseCase createAccount) {
        this.createAccount = createAccount;
    }

    public CreateAccountResponse createAccount(App.Account account) {

        Account responseAccount;
        List<Violation> violations = Collections.emptyList();

        try {
            responseAccount = createAccount.execute(CreateAccountRequest.Builder
                    .aRequest()
                    .withActiveCard(account.activeCard)
                    .withAvailableLimit(account.availableLimit)
                    .build()
            );
        } catch (ViolationException violationException) {
            responseAccount = violationException.getAccount();
            violations = violationException.getViolations();
        } catch (Exception e) {
            //TODO log
            e.printStackTrace();
            throw e;
        }

        return CreateAccountResponse.Builder
                .aResponse()
                .withAccountResponse(
                        responseAccount != null ?
                                CreateAccountResponse.AccountResponse.Builder
                                        .aResponse()
                                        .withActiveCard(responseAccount.isActiveCard())
                                        .withAvailableLimit(responseAccount.getAvailableLimit())
                                        .build()
                                : null
                )
                .withViolations(violations)
                .build();
    }
}
