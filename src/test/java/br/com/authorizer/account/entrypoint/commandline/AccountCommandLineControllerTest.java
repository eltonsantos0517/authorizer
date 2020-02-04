package br.com.authorizer.account.entrypoint.commandline;

import br.com.authorizer.App;
import br.com.authorizer.Violation;
import br.com.authorizer.ViolationException;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.CreateAccount;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

public class AccountCommandLineControllerTest {

    @Test
    public void testCreateAccountWithSuccess() {
        //given a valid response from use case
        CreateAccount createAccountStub = request -> Account.Builder
                .anAccount()
                .withActiveCard(request.isActiveCard())
                .withAvailableLimit(request.getAvailableLimit())
                .build();
        AccountCommandLineController controller = new AccountCommandLineController(createAccountStub);

        //and valid args
        App.Account validAccount = new App.Account(new Random().nextBoolean(), BigDecimal.valueOf(new Random().nextDouble()));

        //when the controller was called with valid args
        CreateAccountResponse response = controller.createAccount(validAccount);

        //then the response has no violations
        Assert.assertNotNull(response.getViolations());
        Assert.assertTrue(response.getViolations().isEmpty());
    }

    @Test
    public void testCreateAccountWithViolationException() {
        //given a valid response from use case
        CreateAccount createAccountStub = request -> {

            throw new ViolationException(
                    Account.Builder
                            .anAccount()
                            .withActiveCard(request.isActiveCard())
                            .withAvailableLimit(request.getAvailableLimit())
                            .build(),
                    Arrays.asList(Violation.ACCOUNT_ALREADY_INITIALIZED, Violation.ACCOUNT_NOT_INITIALIZED)
            );
        };
        AccountCommandLineController controller = new AccountCommandLineController(createAccountStub);

        //and valid args
        App.Account validAccount = new App.Account(new Random().nextBoolean(), BigDecimal.valueOf(new Random().nextDouble()));

        //when the controller was called with valid args
        CreateAccountResponse response = controller.createAccount(validAccount);

        //then the response has no violations
        Assert.assertNotNull(response.getViolations());
        Assert.assertEquals(2, response.getViolations().size());
        Assert.assertTrue(response.getViolations().contains(Violation.ACCOUNT_ALREADY_INITIALIZED.getId()));
        Assert.assertTrue(response.getViolations().contains(Violation.ACCOUNT_NOT_INITIALIZED.getId()));
    }

    @Test
    public void testCreateAccountWithUnexpectedException() {
        //given a valid response from use case
        CreateAccount createAccountStub = request -> {
            throw new RuntimeException();
        };
        AccountCommandLineController controller = new AccountCommandLineController(createAccountStub);

        //and valid args
        App.Account validAccount = new App.Account(new Random().nextBoolean(), BigDecimal.valueOf(new Random().nextDouble()));

        //when the controller was called with valid args
        boolean exceptionWasThrown = false;
        try {
            controller.createAccount(validAccount);
        } catch (Exception e) {
            exceptionWasThrown = true;
        }

        //then the unexpected exception was thrown
        Assert.assertTrue(exceptionWasThrown);
    }
}
