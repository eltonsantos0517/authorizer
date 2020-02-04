package br.com.authorizer.account.entrypoint.commandline;

import br.com.authorizer.App;
import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.CreateAccountRequest;

import java.util.List;

public class AccountConverter {

    static CreateAccountResponse toResponse(Account account, List<Violation> violations) {
        return CreateAccountResponse.Builder
                .aResponse()
                .withAccountResponse(
                        account != null ?
                                CreateAccountResponse.AccountResponse.Builder
                                        .aResponse()
                                        .withActiveCard(account.isActiveCard())
                                        .withAvailableLimit(account.getAvailableLimit())
                                        .build()
                                : null
                )
                .withViolations(violations)
                .build();
    }

    static CreateAccountRequest toRequest(App.Account account) {
        return CreateAccountRequest.Builder
                .aRequest()
                .withActiveCard(account.isActiveCard())
                .withAvailableLimit(account.getAvailableLimit())
                .build();
    }

    private AccountConverter() {
    }
}
