package br.com.authorizer.account.usecase;

import br.com.authorizer.Violation;
import br.com.authorizer.ViolationException;
import br.com.authorizer.account.gateway.AccountGateway;
import br.com.authorizer.account.usecase.validation.ValidationChain;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

public class CreateAccountUseCaseTest {

    @Test
    public void testCreateAccountWithSuccess() throws ViolationException {
        //given a valid request
        CreateAccountRequest request = CreateAccountRequest.Builder
                .aRequest()
                .withAvailableLimit(BigDecimal.valueOf(new Random().nextDouble()))
                .withActiveCard(new Random().nextBoolean())
                .build();

        //and a valid response from gateway
        Account responseAccount = Account.Builder
                .anAccount()
                .withActiveCard(new Random().nextBoolean())
                .withAvailableLimit(BigDecimal.valueOf(new Random().nextDouble()))
                .build();

        boolean[] createAccountWasCalled = new boolean[1];
        AccountGateway gatewayStub = new AccountGateway() {

            @Override
            public Optional<Account> getAccount() {
                return Optional.empty();
            }

            @Override
            public Account createAccount(CreateAccountRequest request) {
                createAccountWasCalled[0] = true;
                return responseAccount;
            }

            @Override
            public Account updateAccount(Account account) {
                return null;
            }
        };

        //and a valid success response from a validation chain
        boolean[] validationChainWasCalled = new boolean[1];
        ValidationChain chainStub = (accountRequest, opPersistedAccount, violations) -> {
            validationChainWasCalled[0] = true;
        };

        CreateAccountUseCase useCase = new CreateAccountUseCase(gatewayStub, chainStub);

        //when the use case is called
        Account response = useCase.execute(request);

        //then the create account was called
        Assert.assertTrue(createAccountWasCalled[0]);

        //and the chain validate was called
        Assert.assertTrue(validationChainWasCalled[0]);

        //and the response is valid
        Assert.assertEquals(responseAccount.isActiveCard(), response.isActiveCard());
        Assert.assertEquals(responseAccount.getAvailableLimit(), response.getAvailableLimit());

    }

    @Test
    public void testCreateAccountWithValidationException() {
        //given a valid request
        CreateAccountRequest request = CreateAccountRequest.Builder
                .aRequest()
                .withAvailableLimit(BigDecimal.valueOf(new Random().nextDouble()))
                .withActiveCard(new Random().nextBoolean())
                .build();

        //and a valid response from gateway
        Account responseAccount = Account.Builder
                .anAccount()
                .withActiveCard(new Random().nextBoolean())
                .withAvailableLimit(BigDecimal.valueOf(new Random().nextDouble()))
                .build();
        ;

        boolean[] createAccountWasCalled = new boolean[1];
        AccountGateway gatewayStub = new AccountGateway() {

            @Override
            public Optional<Account> getAccount() {
                return Optional.of(responseAccount);
            }

            @Override
            public Account createAccount(CreateAccountRequest request) {
                createAccountWasCalled[0] = true;
                return null;
            }

            @Override
            public Account updateAccount(Account account) {
                return null;
            }
        };

        //and a valid success response from a validation chain
        boolean[] validationChainWasCalled = new boolean[1];
        ValidationChain chainStub = (accountRequest, opPersistedAccount, violations) -> {
            validationChainWasCalled[0] = true;
            violations.add(Violation.ACCOUNT_NOT_INITIALIZED);
        };

        CreateAccountUseCase useCase = new CreateAccountUseCase(gatewayStub, chainStub);

        //when the use case is called
        ViolationException exception = null;
        try {
            useCase.execute(request);
        } catch (ViolationException e) {
            exception = e;
        }

        //then the create account was called
        Assert.assertFalse(createAccountWasCalled[0]);

        //and the chain validate was called
        Assert.assertTrue(validationChainWasCalled[0]);

        //and the exception was thrown
        Assert.assertNotNull(exception);
        Assert.assertEquals(exception.getAccount().getAvailableLimit(), responseAccount.getAvailableLimit());
        Assert.assertEquals(exception.getAccount().isActiveCard(), responseAccount.isActiveCard());
        Assert.assertNotNull(exception.getViolations());
        Assert.assertEquals(1, exception.getViolations().size());
        Assert.assertEquals(Violation.ACCOUNT_NOT_INITIALIZED, exception.getViolations().get(0));
    }
}
