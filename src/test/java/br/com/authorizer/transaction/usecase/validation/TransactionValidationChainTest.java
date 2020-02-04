package br.com.authorizer.transaction.usecase.validation;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

public class TransactionValidationChainTest {

    @Test
    public void testValidateWithSuccess() {
        //given a valid chain factory
        final boolean[] validateWasCalled = {false};
        TransactionValidationChainFactory factory = () -> (TransactionValidator)
                (accountRequest, opPersistedAccount, transactions, violations) -> validateWasCalled[0] = true;

        TransactionValidationChain chain = new TransactionValidationChain(factory);

        //when the validate was called
        chain.validate(null, Optional.empty(), Collections.emptyList(), null);

        //then the validator was called
        Assert.assertTrue(validateWasCalled[0]);
    }
}
