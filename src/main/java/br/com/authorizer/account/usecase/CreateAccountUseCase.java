package br.com.authorizer.account.usecase;

import br.com.authorizer.Violation;
import br.com.authorizer.ViolationException;
import br.com.authorizer.account.gateway.AccountGateway;
import br.com.authorizer.account.usecase.validation.ValidationChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateAccountUseCase implements CreateAccount {

    private AccountGateway accountGateway;
    private ValidationChain accountValidationChain;

    public CreateAccountUseCase(AccountGateway accountGateway, ValidationChain accountValidationChain) {
        this.accountGateway = accountGateway;
        this.accountValidationChain = accountValidationChain;
    }

    public Account execute(CreateAccountRequest request) throws ViolationException {

        List<Violation> violations = new ArrayList<>();
        Optional<Account> opAccount = accountGateway.getAccount();

        accountValidationChain.validate(request, opAccount, violations);

        if (!violations.isEmpty()) {
            throw new ViolationException(opAccount.orElse(null), violations);
        }

        return accountGateway.createAccount(request);
    }


}
