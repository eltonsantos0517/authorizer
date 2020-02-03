package br.com.authorizer.account.usecase.validation;

import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.AccountViolation;
import br.com.authorizer.account.usecase.CreateAccountRequest;

import java.util.List;
import java.util.Optional;

public interface AccountValidator {

    void validate(CreateAccountRequest accountRequest, Optional<Account> opPersistedAccount, List<AccountViolation> violations);
}
