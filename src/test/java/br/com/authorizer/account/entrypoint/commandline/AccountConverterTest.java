package br.com.authorizer.account.entrypoint.commandline;

import br.com.authorizer.App;
import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.CreateAccountRequest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AccountConverterTest {

    @Test
    public void testConvertToResponseWithValidParams() {
        //given a valid account and valid violations
        Account account = Account.Builder
                .anAccount()
                .withActiveCard(new Random().nextBoolean())
                .withAvailableLimit(BigDecimal.valueOf(new Random().nextDouble()))
                .build();

        List<Violation> violations =
                Arrays.asList(Violation.CARD_NOT_ACTIVE, Violation.ACCOUNT_ALREADY_INITIALIZED);

        //when the converter was called
        CreateAccountResponse response = AccountConverter.toResponse(account, violations);

        //then the response is valid
        Assert.assertEquals(account.getAvailableLimit(), response.getAccount().getAvailableLimit());
        Assert.assertEquals(account.isActiveCard(), response.getAccount().isActiveCard());
        Assert.assertEquals(2, response.getViolations().size());
        Assert.assertTrue(response.getViolations().contains(Violation.CARD_NOT_ACTIVE.getId()));
        Assert.assertTrue(response.getViolations().contains(Violation.ACCOUNT_ALREADY_INITIALIZED.getId()));
    }

    @Test
    public void testConvertToResponseWithNullParams() {
        //given null account and null violations
        Account account = null;

        List<Violation> violations = null;

        //when the converter was called
        CreateAccountResponse response = AccountConverter.toResponse(account, violations);

        //then the response is valid
        Assert.assertNull(response.getAccount());
        Assert.assertNotNull(response.getViolations());
        Assert.assertTrue(response.getViolations().isEmpty());
    }

    @Test
    public void testConvertToRequestWithValidParams() {
        //given a valid app account
        App.Account account = new App.Account(new Random().nextBoolean(), BigDecimal.valueOf(new Random().nextDouble()));

        //when the converter was called
        CreateAccountRequest request = AccountConverter.toRequest(account);

        //then the request is valid
        Assert.assertEquals(account.getAvailableLimit(), request.getAvailableLimit());
        Assert.assertEquals(account.isActiveCard(), request.isActiveCard());
    }
}
