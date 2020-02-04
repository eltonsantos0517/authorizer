package br.com.authorizer.transaction.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionValidationChain {

    public void validate(CreateTransactionRequest request, Optional<Account> opAccount,
                         List<Transaction> persistedTransactions, List<Violation> violations) {

        getChain().validate(request, opAccount, persistedTransactions, violations);
    }

    private TransactionValidation getChain() {
        return new AccountNotInitializedTransactionValidation(
                new InsufficientLimitIntervalTransactionValidation(
                        new CardNotActiveTransactionValidation(
                                new HighFrequencySmallIntervalTransactionValidation(
                                        new DoubledTransactionValidation(
                                                null
                                        )
                                )
                        )
                )
        );
    }
}
