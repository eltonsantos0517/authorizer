package br.com.authorizer.transaction.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;

import java.util.List;
import java.util.Optional;

class InsufficientLimitIntervalTransactionValidation implements TransactionValidation {

    private TransactionValidation next;

    public InsufficientLimitIntervalTransactionValidation(TransactionValidation next) {
        this.next = next;
    }

    @Override
    public void validate(CreateTransactionRequest request, Optional<Account> opAccount, List<Transaction> persistedTransactions, List<Violation> violations) {

        if (request.getAmount().compareTo(opAccount.get().getAvailableLimit()) > 0) {
            violations.add(Violation.INSUFFICIENT_LIMIT);
        }

        if (next != null) {
            next.validate(request, opAccount, persistedTransactions, violations);
        }

    }
}
