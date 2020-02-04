package br.com.authorizer.transaction.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class HighFrequencySmallIntervalTransactionValidation implements TransactionValidation {

    private TransactionValidation next;

    public HighFrequencySmallIntervalTransactionValidation(TransactionValidation next) {
        this.next = next;
    }

    @Override
    public void validate(CreateTransactionRequest request, Optional<Account> opAccount, List<Transaction> persistedTransactions, List<Violation> violations) {

        ZonedDateTime newTxTime = ZonedDateTime.parse(request.getTime(), DateTimeFormatter.ISO_DATE_TIME).withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime twoMinutesBefore = newTxTime.minusMinutes(2);
        ArrayList<Transaction> transactionsInLast2Mins = new ArrayList<>();
        for (Transaction tx : persistedTransactions) {
            if (ZonedDateTime.parse(tx.getTime(), DateTimeFormatter.ISO_DATE_TIME).withZoneSameInstant(ZoneOffset.UTC).isAfter(twoMinutesBefore)) {
                transactionsInLast2Mins.add(tx);
            }
        }

        if (transactionsInLast2Mins.size() >= 3) {
            violations.add(Violation.HIGH_FREQUENCY_SMALL_INTERVAL);
        }


        if (next != null) {
            next.validate(request, opAccount, persistedTransactions, violations);
        }


    }
}
