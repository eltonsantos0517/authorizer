package br.com.authorizer.transaction.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountNotInitializedTransactionValidatorTest {

    @Test
    public void testValidateWithSuccess() {
        //given some request
        CreateTransactionRequest request = null;

        //and a non empty persistedAccount
        Optional<Account> optionalAccount = Optional.empty();

        //and a violations list
        List<Violation> violations = new ArrayList<>();

        final boolean[] nextValidatorWasCalled = {false};
        AccountNotInitializedTransactionValidator validator = new AccountNotInitializedTransactionValidator(
                (accountRequest, opPersistedAccount, transactions, violations1) -> {
                    nextValidatorWasCalled[0] = true;
                });

        //when the validator is called
        validator.validate(request, optionalAccount, null, violations);

        //then a account-not-initialized violation was added to list
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals(Violation.ACCOUNT_NOT_INITIALIZED, violations.get(0));
        Assert.assertFalse(nextValidatorWasCalled[0]);
    }

    @Test
    public void testValidateWithSuccessAndNotViolation() {
        //given some request
        CreateTransactionRequest request = null;

        //and a empty persistedAccount
        Optional<Account> optionalAccount = Optional.of(
                Account.Builder
                        .anAccount()
                        .withActiveCard(true)
                        .build()
        );

        //and a violations list
        List<Violation> violations = new ArrayList<>();

        final boolean[] nextValidatorWasCalled = {false};
        AccountNotInitializedTransactionValidator validator = new AccountNotInitializedTransactionValidator(
                (accountRequest, opPersistedAccount, transactions, violations1) -> {
                    nextValidatorWasCalled[0] = true;
                }
        );

        //when the validator is called
        validator.validate(request, optionalAccount, null, violations);

        //then none violation was added to list
        Assert.assertEquals(0, violations.size());
        Assert.assertTrue(nextValidatorWasCalled[0]);
    }
}
