package br.com.authorizer.transaction.usecase;

import br.com.authorizer.Violation;
import br.com.authorizer.ViolationException;
import br.com.authorizer.account.gateway.AccountGateway;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.gateway.TransactionGateway;
import br.com.authorizer.transaction.usecase.validation.TransactionValidationChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateTransactionUseCase {

    private AccountGateway accountGateway;
    private TransactionGateway transactionGateway;
    private TransactionValidationChain transactionValidationChain;

    public CreateTransactionUseCase(AccountGateway accountGateway, TransactionGateway transactionGateway,
                                    TransactionValidationChain transactionValidationChain) {
        this.accountGateway = accountGateway;
        this.transactionGateway = transactionGateway;
        this.transactionValidationChain = transactionValidationChain;
    }

    public Account execute(CreateTransactionRequest request) throws ViolationException {

        List<Violation> violations = new ArrayList<>();
        Optional<Account> opAccount = accountGateway.getAccount();
        List<Transaction> transactions = transactionGateway.getAllTransactions();

        transactionValidationChain.validate(request, opAccount, transactions, violations);

        if (!violations.isEmpty()) {
            throw new ViolationException(opAccount.orElse(null), violations);
        }

        transactionGateway.createTransaction(request);

        return accountGateway.updateAccount(
                new Account(
                        opAccount.get().isActiveCard(),
                        opAccount.get().getAvailableLimit().subtract(request.getAmount())
                )
        );
    }
}
