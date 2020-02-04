package br.com.authorizer.transaction.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class CardNotActiveTransactionValidation implements TransactionValidation {

    private TransactionValidation next;

    public CardNotActiveTransactionValidation(TransactionValidation next) {
        this.next = next;
    }

    @Override
    public void validate(CreateTransactionRequest request, Optional<Account> opAccount, List<Transaction> persistedTransactions, List<Violation> violations) {


        if (!opAccount.orElseGet(() -> new Account(false, BigDecimal.ZERO))
                .isActiveCard()) {
            violations.add(Violation.CARD_NOT_ACTIVE);
        }

        if (next != null) {
            next.validate(request, opAccount, persistedTransactions, violations);
        }

    }
}
