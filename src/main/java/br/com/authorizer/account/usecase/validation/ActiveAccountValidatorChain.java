package br.com.authorizer.account.usecase.validation;

import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.AccountViolation;
import br.com.authorizer.account.usecase.CreateAccountRequest;

import java.util.List;
import java.util.Optional;

public class ActiveAccountValidatorChain implements AccountValidator {

    private AccountValidator nextValidation;

    public ActiveAccountValidatorChain(AccountValidator nextValidation) {
        this.nextValidation = nextValidation;
    }

    @Override
    public void validate(CreateAccountRequest accountRequest, Optional<Account> opPersistedAccount, List<AccountViolation> violations) {


        if (opPersistedAccount.isPresent()) {
            violations.add(AccountViolation.ACCOUNT_ALREADY_INITIALIZED);
        }

        if (nextValidation != null) {
            nextValidation.validate(accountRequest, opPersistedAccount, violations);
        }
    }
}
