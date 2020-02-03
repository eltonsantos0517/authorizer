package br.com.authorizer.account.usecase;

import br.com.authorizer.account.gateway.AccountGateway;
import br.com.authorizer.account.usecase.validation.AccountValidator;
import br.com.authorizer.account.usecase.validation.ActiveAccountValidatorChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateAccountUseCase {

    private AccountGateway accountGateway;

    public CreateAccountUseCase(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Account execute(CreateAccountRequest request) throws AccountViolationException {

        List<AccountViolation> violations = new ArrayList<>();
        Optional<Account> opAccount = accountGateway.getAccount();

        getValidationChain().validate(request, opAccount, violations);

        if (!violations.isEmpty()) {
            throw new AccountViolationException(opAccount.orElse(null), violations);
        }

        return accountGateway.createAccount(request);
    }

    private AccountValidator getValidationChain() {
        return new ActiveAccountValidatorChain(null);
    }
}
