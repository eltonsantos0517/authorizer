package br.com.authorizer.account.gateway;

import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.CreateAccountRequest;

import java.util.Optional;

public interface AccountGateway {

    Optional<Account> getAccount();

    Account createAccount(CreateAccountRequest request);
    Account updateAccount(Account account);
}
