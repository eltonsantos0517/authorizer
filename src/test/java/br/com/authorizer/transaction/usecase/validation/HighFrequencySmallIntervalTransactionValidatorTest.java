package br.com.authorizer.transaction.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HighFrequencySmallIntervalTransactionValidatorTest {

    @Test
    public void testValidateWithSuccess() {
        //given some request
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withTime("2019-02-13T09:01:59.000Z")
                .build();

        //and a non empty persistedAccount
        Optional<Account> optionalAccount = Optional.empty();

        //and a violations list
        List<Violation> violations = new ArrayList<>();

        //and a valid transactions list
        List<Transaction> transactions = Arrays.asList(
                Transaction.Builder
                        .aTransaction()
                        .withTime("2019-02-13T09:00:00.000Z")
                        .build(),
                Transaction.Builder
                        .aTransaction()
                        .withTime("2019-02-13T09:00:30.000Z")
                        .build(),
                Transaction.Builder
                        .aTransaction()
                        .withTime("2019-02-13T09:01:58.000Z")
                        .build()
        );

        HighFrequencySmallIntervalTransactionValidator validator = new HighFrequencySmallIntervalTransactionValidator(null);

        //when the validator is called
        validator.validate(request, optionalAccount, transactions, violations);

        //then a high-frequency-small-interval violation was added to list
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals(Violation.HIGH_FREQUENCY_SMALL_INTERVAL, violations.get(0));
    }

    @Test
    public void testValidateWithSuccessAndNotViolation() {
        //given some request
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withTime("2019-02-13T09:05:00.000Z")
                .build();

        //and a empty persistedAccount
        Optional<Account> optionalAccount = Optional.empty();

        //and a violations list
        List<Violation> violations = new ArrayList<>();

        //and a valid transactions list
        //and a valid transactions list
        List<Transaction> transactions = Arrays.asList(
                Transaction.Builder
                        .aTransaction()
                        .withTime("2019-02-13T09:00:00.000Z")
                        .build(),
                Transaction.Builder
                        .aTransaction()
                        .withTime("2019-02-13T09:04:30.000Z")
                        .build(),
                Transaction.Builder
                        .aTransaction()
                        .withTime("2019-02-13T09:04:58.000Z")
                        .build()
        );

        final boolean[] nextValidatorWasCalled = {false};
        HighFrequencySmallIntervalTransactionValidator validator = new HighFrequencySmallIntervalTransactionValidator(
                (accountRequest, opPersistedAccount, transactionsList, violations1) -> {
                    nextValidatorWasCalled[0] = true;
                }
        );


        //when the validator is called
        validator.validate(request, optionalAccount, transactions, violations);

        //then none violation was added to list
        Assert.assertEquals(0, violations.size());
        Assert.assertTrue(nextValidatorWasCalled[0]);
    }
}
