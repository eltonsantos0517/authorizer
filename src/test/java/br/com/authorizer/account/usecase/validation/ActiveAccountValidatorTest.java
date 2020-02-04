package br.com.authorizer.account.usecase.validation;

import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.CreateAccountRequest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ActiveAccountValidatorTest {

    @Test
    public void testValidateWithSuccess() {
        //given some request
        CreateAccountRequest request = null;

        //and a non empty persistedAccount
        Optional<Account> optionalAccount = Optional.of(
                Account.Builder
                        .anAccount()
                        .withActiveCard(new Random().nextBoolean())
                        .withAvailableLimit(BigDecimal.valueOf(new Random().nextDouble()))
                        .build()
        );

        //and a violations list
        List<Violation> violations = new ArrayList<>();

        ActiveAccountValidator validator = new ActiveAccountValidator(null);

        //when the validator is called
        validator.validate(request, optionalAccount, violations);

        //then a account-already-initialized violation was added to list
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals(Violation.ACCOUNT_ALREADY_INITIALIZED, violations.get(0));
    }

    @Test
    public void testValidateWithSuccessAndNotViolation() {
        //given some request
        CreateAccountRequest request = null;

        //and a empty persistedAccount
        Optional<Account> optionalAccount = Optional.empty();

        //and a violations list
        List<Violation> violations = new ArrayList<>();

        final boolean[] nextValidatorWasCalled = {false};
        ActiveAccountValidator validator = new ActiveAccountValidator(
                (accountRequest, opPersistedAccount, violations1) -> {
                    nextValidatorWasCalled[0] = true;
                }
        );

        //when the validator is called
        validator.validate(request, optionalAccount, violations);

        //then a account-already-initialized violation was added to list
        Assert.assertEquals(0, violations.size());
        Assert.assertTrue(nextValidatorWasCalled[0]);
    }
}
