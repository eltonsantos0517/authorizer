package br.com.authorizer.account.usecase.validation;

import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.CreateAccountRequest;

import java.util.List;
import java.util.Optional;

public class AccountValidationChain {

    public void validate(CreateAccountRequest accountRequest, Optional<Account> opPersistedAccount, List<Violation> violations) {
        getChain().validate(accountRequest, opPersistedAccount, violations);
    }

    private AccountValidator getChain() {
        return new ActiveAccountValidator(null);
    }
}
