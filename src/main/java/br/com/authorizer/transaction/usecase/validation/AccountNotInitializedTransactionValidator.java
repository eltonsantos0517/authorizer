package br.com.authorizer.transaction.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;

import java.util.List;
import java.util.Optional;

class AccountNotInitializedTransactionValidator implements TransactionValidator {

    private TransactionValidator next;

    public AccountNotInitializedTransactionValidator(TransactionValidator next) {
        this.next = next;
    }

    @Override
    public void validate(CreateTransactionRequest request, Optional<Account> opAccount, List<Transaction> persistedTransactions, List<Violation> violations) {

        if (!opAccount.isPresent()) {
            violations.add(Violation.ACCOUNT_NOT_INITIALIZED);
            //The violation breaks de chain, because other rules isn't validate if account was not created
        } else {
            next.validate(request, opAccount, persistedTransactions, violations);
        }
    }
}
