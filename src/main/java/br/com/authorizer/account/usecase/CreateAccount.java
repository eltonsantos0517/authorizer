package br.com.authorizer.account.usecase;

import br.com.authorizer.ViolationException;

public interface CreateAccount {

    Account execute(CreateAccountRequest request) throws ViolationException;
}
