package br.com.authorizer.account.gateway.client;

import br.com.authorizer.account.usecase.Account;

import java.util.Optional;

public class AccountInMemoryDatabase {

    private Account account;

    public Optional<Account> getAccount() {
        return Optional.ofNullable(account);
    }

    public Account createAccount(Account account) {
        this.account = account;
        return this.account;
    }
}
