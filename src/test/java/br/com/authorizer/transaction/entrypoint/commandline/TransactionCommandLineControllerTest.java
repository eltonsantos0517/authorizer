package br.com.authorizer.transaction.entrypoint.commandline;

import br.com.authorizer.App;
import br.com.authorizer.Violation;
import br.com.authorizer.ViolationException;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransaction;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class TransactionCommandLineControllerTest {

    @Test
    public void testCreateTransactionWithSuccess() {
        //given a valid response from use case
        Account accountResponse = Account.Builder
                .anAccount()
                .withActiveCard(new Random().nextBoolean())
                .withAvailableLimit(BigDecimal.valueOf(new Random().nextDouble()))
                .build();

        CreateTransaction createTransactionStub = request -> accountResponse;

        TransactionCommandLineController controller = new TransactionCommandLineController(createTransactionStub);

        //and valid args
        App.Transaction appTransaction = new App.Transaction(BigDecimal.valueOf(new Random().nextDouble()), UUID.randomUUID().toString(), UUID.randomUUID().toString());

        //when the controller was called with valid args
        CreateTransactionResponse response = controller.createTransaction(appTransaction);

        //then the response has no violations
        Assert.assertNotNull(response.getViolations());
        Assert.assertTrue(response.getViolations().isEmpty());
    }

    @Test
    public void testCreateAccountWithViolationException() {
        //given a valid response from use case
        CreateTransaction createTransactionStub = request -> {

            throw new ViolationException(
                    Account.Builder
                            .anAccount()
                            .withActiveCard(new Random().nextBoolean())
                            .withAvailableLimit(BigDecimal.valueOf(new Random().nextDouble()))
                            .build(),
                    Arrays.asList(Violation.ACCOUNT_ALREADY_INITIALIZED, Violation.ACCOUNT_NOT_INITIALIZED)
            );
        };
        TransactionCommandLineController controller = new TransactionCommandLineController(createTransactionStub);

        //and valid args
        App.Transaction AppTransaction = new App.Transaction(BigDecimal.valueOf(new Random().nextDouble()), UUID.randomUUID().toString(), UUID.randomUUID().toString());

        //when the controller was called with valid args
        CreateTransactionResponse response = controller.createTransaction(AppTransaction);

        //then the response has no violations
        Assert.assertNotNull(response.getViolations());
        Assert.assertEquals(2, response.getViolations().size());
        Assert.assertTrue(response.getViolations().contains(Violation.ACCOUNT_ALREADY_INITIALIZED.getId()));
        Assert.assertTrue(response.getViolations().contains(Violation.ACCOUNT_NOT_INITIALIZED.getId()));
    }

    @Test
    public void testCreateAccountWithUnexpectedException() {
        //given a valid response from use case
        CreateTransaction createTransactionStub = request -> {
            throw new RuntimeException();
        };
        TransactionCommandLineController controller = new TransactionCommandLineController(createTransactionStub);

        //and valid args
        App.Transaction AppTransaction = new App.Transaction(BigDecimal.valueOf(new Random().nextDouble()), UUID.randomUUID().toString(), UUID.randomUUID().toString());

        //when the controller was called with valid args
        boolean exceptionWasThrown = false;
        try {
            controller.createTransaction(AppTransaction);
        } catch (Exception e) {
            exceptionWasThrown = true;
        }

        //then the unexpected exception was thrown
        Assert.assertTrue(exceptionWasThrown);
    }
}
