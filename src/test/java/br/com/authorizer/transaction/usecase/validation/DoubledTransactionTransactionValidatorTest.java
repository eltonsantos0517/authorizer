package br.com.authorizer.transaction.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import br.com.authorizer.transaction.usecase.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

public class DoubledTransactionTransactionValidatorTest {

    @Test
    public void testValidateWithSuccess() {
        //given some request
        String merchant = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.TEN;
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withTime("2019-02-13T09:01:59.000Z")
                .withAmount(amount)
                .withMerchant(merchant)
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
                        .withMerchant(merchant)
                        .withAmount(amount)
                        .build()
        );

        DoubledTransactionValidator validator = new DoubledTransactionValidator(null);

        //when the validator is called
        validator.validate(request, optionalAccount, transactions, violations);

        //then a doubled-transaction violation was added to list
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals(Violation.DOUBLED_TRANSACTION, violations.get(0));
    }

    @Test
    public void testValidateWithSuccessAndNotViolationAfter2Minutes() {
        //given some request
        String merchant = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.TEN;
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withTime("2019-02-13T09:05:00.000Z")
                .withAmount(amount)
                .withMerchant(merchant)
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
                        .withMerchant(merchant)
                        .withAmount(amount)
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

    @Test
    public void testValidateWithSuccessAndNotViolationWithDifferentMerchant() {
        //given some request
        String merchant = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.TEN;
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withTime("2019-02-13T09:01:59.000Z")
                .withAmount(amount)
                .withMerchant(merchant)
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
                        .withMerchant(UUID.randomUUID().toString())
                        .withAmount(amount)
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

    @Test
    public void testValidateWithSuccessAndNotViolationWithDifferentAmount() {
        //given some request
        String merchant = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.TEN;
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withTime("2019-02-13T09:01:59.000Z")
                .withAmount(amount)
                .withMerchant(merchant)
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
                        .withMerchant(merchant)
                        .withAmount(BigDecimal.ONE)
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
