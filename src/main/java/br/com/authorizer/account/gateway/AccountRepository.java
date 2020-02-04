package br.com.authorizer.account.gateway;

import br.com.authorizer.account.gateway.client.AccountInMemoryDatabase;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.CreateAccountRequest;

import java.util.Optional;

public class AccountRepository implements AccountGateway {

    private AccountInMemoryDatabase accountDatabase;

    public AccountRepository(AccountInMemoryDatabase accountDatabase) {
        this.accountDatabase = accountDatabase;
    }

    @Override
    public Optional<Account> getAccount() {
        return accountDatabase.getAccount();
    }

    @Override
    public Account createAccount(CreateAccountRequest request) {
        return accountDatabase.createAccount(new Account(request.isActiveCard(), request.getAvailableLimit()));
    }

    @Override
    public Account updateAccount(Account account) {
        return accountDatabase.updateAccount(account);
    }
}
