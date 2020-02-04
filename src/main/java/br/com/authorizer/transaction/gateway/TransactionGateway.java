package br.com.authorizer.transaction.gateway;

import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;

import java.util.List;

public interface TransactionGateway {

    List<Transaction> getAllTransactions();

    void createTransaction(CreateTransactionRequest request);


}
