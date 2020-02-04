package br.com.authorizer.account.entrypoint.commandline;

import br.com.authorizer.App;
import br.com.authorizer.Violation;
import br.com.authorizer.ViolationException;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.account.usecase.CreateAccount;

import java.util.Collections;
import java.util.List;

public class AccountCommandLineController {

    private CreateAccount createAccount;

    public AccountCommandLineController(CreateAccount createAccount) {
        this.createAccount = createAccount;
    }

    public CreateAccountResponse createAccount(App.Account account) {

        Account responseAccount;
        List<Violation> violations = Collections.emptyList();

        try {
            responseAccount = createAccount.execute(
                    AccountConverter.toRequest(account)
            );
        } catch (ViolationException violationException) {
            responseAccount = violationException.getAccount();
            violations = violationException.getViolations();
        } catch (Exception e) {
            //TODO log
            e.printStackTrace();
            throw e;
        }

        return AccountConverter.toResponse(responseAccount, violations);
    }
}
