package br.com.authorizer.transaction.entrypoint.commandline;

import br.com.authorizer.App;
import br.com.authorizer.Violation;
import br.com.authorizer.ViolationException;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionUseCase;

import java.util.Collections;
import java.util.List;

public class TransactionCommandLineController {

    private CreateTransactionUseCase createTransaction;

    public TransactionCommandLineController(CreateTransactionUseCase createTransaction) {
        this.createTransaction = createTransaction;
    }

    public CreateTransactionResponse createTransaction(App.Transaction transaction) {

        Account responseAccount;
        List<Violation> violations = Collections.emptyList();

        try {
            responseAccount = createTransaction.execute(
                    TransactionConverter.toRequest(transaction)
            );
        } catch (ViolationException violationException) {
            responseAccount = violationException.getAccount();
            violations = violationException.getViolations();
        } catch (Exception e) {
            //TODO log
            e.printStackTrace();
            throw e;
        }


        return TransactionConverter.toResponse(responseAccount, violations);
    }
}
