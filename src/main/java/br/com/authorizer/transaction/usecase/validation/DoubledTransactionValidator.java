package br.com.authorizer.transaction.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class DoubledTransactionValidator implements TransactionValidator {

    private TransactionValidator next;

    public DoubledTransactionValidator(TransactionValidator next) {
        this.next = next;
    }

    @Override
    public void validate(CreateTransactionRequest request, Optional<Account> opAccount, List<Transaction> persistedTransactions, List<Violation> violations) {

        ZonedDateTime twoMinutesBefore = ZonedDateTime.parse(request.getTime(), DateTimeFormatter.ISO_DATE_TIME)
                .withZoneSameInstant(ZoneOffset.UTC)
                .minusMinutes(2);

        if (Optional.ofNullable(persistedTransactions).orElseGet(Collections::emptyList).stream()
                .anyMatch(tx -> ZonedDateTime.parse(tx.getTime(), DateTimeFormatter.ISO_DATE_TIME)
                        .withZoneSameInstant(ZoneOffset.UTC)
                        .isAfter(twoMinutesBefore) &&
                        tx.getAmount().equals(request.getAmount()) &&
                        tx.getMerchant().equals(request.getMerchant()))) {
            violations.add(Violation.DOUBLED_TRANSACTION);
        }


        if (next != null) {
            next.validate(request, opAccount, persistedTransactions, violations);
        }


    }
}
