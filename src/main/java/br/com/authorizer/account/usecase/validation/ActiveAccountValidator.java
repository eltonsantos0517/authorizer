package br.com.authorizer.account.usecase.validation;

import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.CreateAccountRequest;

import java.util.List;
import java.util.Optional;

class ActiveAccountValidator implements AccountValidator {

    private AccountValidator nextValidation;

    public ActiveAccountValidator(AccountValidator nextValidation) {
        this.nextValidation = nextValidation;
    }

    @Override
    public void validate(CreateAccountRequest accountRequest, Optional<Account> opPersistedAccount, List<Violation> violations) {


        if (opPersistedAccount.isPresent()) {
            violations.add(Violation.ACCOUNT_ALREADY_INITIALIZED);
        }

        if (nextValidation != null) {
            nextValidation.validate(accountRequest, opPersistedAccount, violations);
        }
    }
}
