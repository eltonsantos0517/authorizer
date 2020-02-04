package br.com.authorizer.transaction.usecase;

import br.com.authorizer.ViolationException;
import br.com.authorizer.account.usecase.Account;

public interface CreateTransaction {

    Account execute(CreateTransactionRequest request) throws ViolationException;
}
