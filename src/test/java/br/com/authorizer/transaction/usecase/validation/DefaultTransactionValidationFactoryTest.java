package br.com.authorizer.transaction.usecase.validation;

import org.junit.Assert;
import org.junit.Test;

public class DefaultTransactionValidationFactoryTest {


    @Test
    public void testGetDefaultChain() {
        //given a valid factory
        DefaultTransactionValidationChainFactory factory = new DefaultTransactionValidationChainFactory();

        //when the factory was called
        TransactionValidator validator = factory.getChain();

        //then the response is a AccountNotInitializedTransactionValidation
        Assert.assertTrue(validator instanceof AccountNotInitializedTransactionValidator);
    }

}
