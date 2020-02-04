package br.com.authorizer.account.usecase.validation;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class AccountValidationChainTest {

    @Test
    public void testValidateWithSuccess() {
        //given a valid chain factory
        final boolean[] validateWasCalled = {false};
        AccountValidationChainFactory factory = () -> (AccountValidator)
                (accountRequest, opPersistedAccount, violations) -> validateWasCalled[0] = true;

        AccountValidationChain chain = new AccountValidationChain(factory);

        //when the validate was called
        chain.validate(null, Optional.empty(), null);

        //then the validator was called
        Assert.assertTrue(validateWasCalled[0]);
    }
}
