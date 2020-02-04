package br.com.authorizer.transaction.gateway.client;

import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionInMemoryDatabase {

    private List<Transaction> transactions;

    public TransactionInMemoryDatabase() {
        this.transactions = new ArrayList<>();
    }

    public List<Transaction> getAllTransactions() {
        return this.transactions;
    }

    public void createTransaction(CreateTransactionRequest request) {
        transactions.add(new Transaction(request.getAmount(), request.getMerchant(), request.getTime()));

    }
}
