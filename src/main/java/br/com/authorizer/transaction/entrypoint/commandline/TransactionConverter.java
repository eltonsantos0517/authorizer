package br.com.authorizer.transaction.entrypoint.commandline;

import br.com.authorizer.App;
import br.com.authorizer.Violation;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.usecase.CreateTransactionRequest;

import java.util.List;

public class TransactionConverter {

    static CreateTransactionResponse toResponse(Account account, List<Violation> violations) {
        return CreateTransactionResponse.Builder
                .aResponse()
                .withAccountResponse(
                        account != null ?
                                CreateTransactionResponse.AccountResponse.Builder
                                        .aResponse()
                                        .withActiveCard(account.isActiveCard())
                                        .withAvailableLimit(account.getAvailableLimit())
                                        .build()
                                : null
                )
                .withViolations(violations)
                .build();
    }

    static CreateTransactionRequest toRequest(App.Transaction transaction) {
        return CreateTransactionRequest.Builder
                .aRequest()
                .withTime(transaction.time)
                .withMerchant(transaction.merchant)
                .withAmount(transaction.amount)
                .build();
    }


    private TransactionConverter() {
    }
}
