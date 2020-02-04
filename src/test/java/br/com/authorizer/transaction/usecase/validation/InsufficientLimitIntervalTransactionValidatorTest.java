package br.com.authorizer.transaction.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class InsufficientLimitIntervalTransactionValidatorTest {

    @Test
    public void testValidateWithSuccess() {
        //given some request
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withAmount(BigDecimal.valueOf(100))
                .build();

        //and a non empty persistedAccount
        Optional<Account> optionalAccount = Optional.of(
                Account.Builder
                        .anAccount()
                        .withActiveCard(new Random().nextBoolean())
                        .withAvailableLimit(BigDecimal.valueOf(99))
                        .build()
        );

        //and a violations list
        List<Violation> violations = new ArrayList<>();

        InsufficientLimitIntervalTransactionValidator validator = new InsufficientLimitIntervalTransactionValidator(null);

        //when the validator is called
        validator.validate(request, optionalAccount, null, violations);

        //then a account-already-initialized violation was added to list
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals(Violation.INSUFFICIENT_LIMIT, violations.get(0));
    }

    @Test
    public void testValidateWithExactlyAmount() {
        //given some request
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withAmount(BigDecimal.valueOf(100))
                .build();

        //and a empty persistedAccount
        Optional<Account> optionalAccount = Optional.of(
                Account.Builder
                        .anAccount()
                        .withActiveCard(new Random().nextBoolean())
                        .withAvailableLimit(BigDecimal.valueOf(100))
                        .build()
        );

        //and a violations list
        List<Violation> violations = new ArrayList<>();

        final boolean[] nextValidatorWasCalled = {false};
        InsufficientLimitIntervalTransactionValidator validator = new InsufficientLimitIntervalTransactionValidator(
                (accountRequest, opPersistedAccount, transactions, violations1) -> {
                    nextValidatorWasCalled[0] = true;
                }
        );

        //when the validator is called
        validator.validate(request, optionalAccount, null, violations);

        //then none violation was added to list
        Assert.assertTrue(nextValidatorWasCalled[0]);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void testValidateWithMinorRequestAmount() {
        //given some request
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withAmount(BigDecimal.valueOf(50))
                .build();

        //and a empty persistedAccount
        Optional<Account> optionalAccount = Optional.of(
                Account.Builder
                        .anAccount()
                        .withActiveCard(new Random().nextBoolean())
                        .withAvailableLimit(BigDecimal.valueOf(100))
                        .build()
        );

        //and a violations list
        List<Violation> violations = new ArrayList<>();

        final boolean[] nextValidatorWasCalled = {false};
        InsufficientLimitIntervalTransactionValidator validator = new InsufficientLimitIntervalTransactionValidator(
                (accountRequest, opPersistedAccount, transactions, violations1) -> {
                    nextValidatorWasCalled[0] = true;
                }
        );

        //when the validator is called
        validator.validate(request, optionalAccount, null, violations);

        //then none violation was added to list
        Assert.assertTrue(nextValidatorWasCalled[0]);
        Assert.assertEquals(0, violations.size());
    }
}
