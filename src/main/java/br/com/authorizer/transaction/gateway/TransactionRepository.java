package br.com.authorizer.transaction.gateway;

import br.com.authorizer.transaction.gateway.client.TransactionInMemoryDatabase;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;

import java.util.List;

public class TransactionRepository implements TransactionGateway {

    private TransactionInMemoryDatabase transactionDatabase;

    public TransactionRepository(TransactionInMemoryDatabase transactionDatabase) {
        this.transactionDatabase = transactionDatabase;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDatabase.getAllTransactions();
    }

    @Override
    public void createTransaction(CreateTransactionRequest request) {
        transactionDatabase.createTransaction(request);
    }
}
