package br.com.authorizer.transaction.usecase;

import br.com.authorizer.Violation;
import br.com.authorizer.ViolationException;
import br.com.authorizer.account.gateway.AccountGateway;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.CreateAccountRequest;
import br.com.authorizer.transaction.gateway.TransactionGateway;
import br.com.authorizer.transaction.usecase.validation.ValidationChain;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

public class CreateTransactionUseCaseTest {

    @Test
    public void testCreateTransactionWithSuccess() throws ViolationException {
        //given a valid request
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withAmount(BigDecimal.valueOf(new Random().nextDouble()))
                .withMerchant(UUID.randomUUID().toString())
                .withTime(UUID.randomUUID().toString())
                .build();

        //and a valid response from account gateway
        Account responseAccount = Account.Builder
                .anAccount()
                .withActiveCard(new Random().nextBoolean())
                .withAvailableLimit(BigDecimal.valueOf(new Random().nextDouble()))
                .build();

        boolean[] updateAccountWasCalledWithValidArgs = new boolean[1];
        AccountGateway accountGatewayStub = new AccountGateway() {

            @Override
            public Optional<Account> getAccount() {
                return Optional.of(responseAccount);
            }

            @Override
            public Account createAccount(CreateAccountRequest request) {
                return null;
            }

            @Override
            public Account updateAccount(Account account) {

                if (account.isActiveCard() == responseAccount.isActiveCard() &&
                        account.getAvailableLimit().equals(responseAccount.getAvailableLimit().subtract(request.getAmount()))) {
                    updateAccountWasCalledWithValidArgs[0] = true;
                }

                return account;
            }
        };

        //and a valid response from transaction gateway
        List<Transaction> persistedTransactions = Arrays.asList(
                Transaction.Builder
                        .aTransaction()
                        .build(),
                Transaction.Builder
                        .aTransaction()
                        .build()
        );

        boolean[] createTransactionWasCalled = new boolean[1];
        TransactionGateway transactionGatewayStub = new TransactionGateway() {
            @Override
            public List<Transaction> getAllTransactions() {
                return persistedTransactions;
            }

            @Override
            public void createTransaction(CreateTransactionRequest request) {
                createTransactionWasCalled[0] = true;
            }
        };

        //and a valid success response from a validation chain
        boolean[] validationChainWasCalled = new boolean[1];
        ValidationChain chainStub = (accountRequest, opPersistedAccount, transactions, violations) -> {
            validationChainWasCalled[0] = true;
        };

        CreateTransactionUseCase useCase = new CreateTransactionUseCase(accountGatewayStub, transactionGatewayStub, chainStub);

        //when the use case is called
        Account response = useCase.execute(request);

        //then the create transaction was called
        Assert.assertTrue(createTransactionWasCalled[0]);

        //and the chain validate was called
        Assert.assertTrue(validationChainWasCalled[0]);

        //then the create transaction was called
        Assert.assertTrue(updateAccountWasCalledWithValidArgs[0]);
    }

    @Test
    public void testCreateTransactionWithValidationException() {
        //given a valid request
        CreateTransactionRequest request = CreateTransactionRequest.Builder
                .aRequest()
                .withAmount(BigDecimal.valueOf(new Random().nextDouble()))
                .withMerchant(UUID.randomUUID().toString())
                .withTime(UUID.randomUUID().toString())
                .build();

        //and a valid response from gateway
        Account responseAccount = Account.Builder
                .anAccount()
                .withActiveCard(new Random().nextBoolean())
                .withAvailableLimit(BigDecimal.valueOf(new Random().nextDouble()))
                .build();
        ;

        boolean[] updateAccountWasCalledWithValidArgs = new boolean[1];
        AccountGateway accountGatewayStub = new AccountGateway() {

            @Override
            public Optional<Account> getAccount() {
                return Optional.of(responseAccount);
            }

            @Override
            public Account createAccount(CreateAccountRequest request) {
                return null;
            }

            @Override
            public Account updateAccount(Account account) {

                if (account.isActiveCard() == responseAccount.isActiveCard() &&
                        account.getAvailableLimit().equals(responseAccount.getAvailableLimit().subtract(request.getAmount()))) {
                    updateAccountWasCalledWithValidArgs[0] = true;
                }

                return account;
            }
        };

        List<Transaction> persistedTransactions = Arrays.asList(
                Transaction.Builder
                        .aTransaction()
                        .build(),
                Transaction.Builder
                        .aTransaction()
                        .build()
        );

        boolean[] createTransactionWasCalled = new boolean[1];
        TransactionGateway transactionGatewayStub = new TransactionGateway() {
            @Override
            public List<Transaction> getAllTransactions() {
                return persistedTransactions;
            }

            @Override
            public void createTransaction(CreateTransactionRequest request) {
                createTransactionWasCalled[0] = true;
            }
        };

        //and a valid success response from a validation chain
        boolean[] validationChainWasCalled = new boolean[1];
        ValidationChain chainStub = (accountRequest, opPersistedAccount, transactions, violations) -> {
            validationChainWasCalled[0] = true;
            violations.add(Violation.ACCOUNT_NOT_INITIALIZED);
        };

        CreateTransactionUseCase useCase = new CreateTransactionUseCase(accountGatewayStub, transactionGatewayStub, chainStub);

        //when the use case is called
        ViolationException exception = null;
        try {
            useCase.execute(request);
        } catch (ViolationException e) {
            exception = e;
        }

        //and the chain validate not was called
        Assert.assertTrue(validationChainWasCalled[0]);

        //then the create transaction was called
        Assert.assertFalse(createTransactionWasCalled[0]);

        //then the update account not was called
        Assert.assertFalse(updateAccountWasCalledWithValidArgs[0]);

        //and the exception was thrown
        Assert.assertNotNull(exception);
        Assert.assertEquals(exception.getAccount().getAvailableLimit(), responseAccount.getAvailableLimit());
        Assert.assertEquals(exception.getAccount().isActiveCard(), responseAccount.isActiveCard());
        Assert.assertNotNull(exception.getViolations());
        Assert.assertEquals(1, exception.getViolations().size());
        Assert.assertEquals(Violation.ACCOUNT_NOT_INITIALIZED, exception.getViolations().get(0));
    }
}
