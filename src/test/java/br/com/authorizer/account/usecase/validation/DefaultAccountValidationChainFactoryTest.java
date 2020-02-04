package br.com.authorizer.account.usecase.validation;

import org.junit.Assert;
import org.junit.Test;

public class DefaultAccountValidationChainFactoryTest {


    @Test
    public void testGetDefaultChain() {
        //given a valid factory
        DefaultAccountValidationChainFactory factory = new DefaultAccountValidationChainFactory();

        //when the factory was called
        AccountValidator validator = factory.getChain();

        //then the response is a ActiveAccountValidator
        Assert.assertTrue(validator instanceof ActiveAccountValidator);
    }

}
