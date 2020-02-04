package br.com.authorizer.transaction.usecase;

import br.com.authorizer.Violation;
import br.com.authorizer.ViolationException;
import br.com.authorizer.account.gateway.AccountGateway;
import br.com.authorizer.account.usecase.Account;
import br.com.authorizer.transaction.gateway.TransactionGateway;
import br.com.authorizer.transaction.usecase.validation.ValidationChain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateTransactionUseCase implements CreateTransaction {

    private AccountGateway accountGateway;
    private TransactionGateway transactionGateway;
    private ValidationChain validationChain;

    public CreateTransactionUseCase(AccountGateway accountGateway, TransactionGateway transactionGateway,
                                    ValidationChain validationChain) {
        this.accountGateway = accountGateway;
        this.transactionGateway = transactionGateway;
        this.validationChain = validationChain;
    }

    public Account execute(CreateTransactionRequest request) throws ViolationException {

        List<Violation> violations = new ArrayList<>();
        Optional<Account> opAccount = accountGateway.getAccount();
        List<Transaction> transactions = transactionGateway.getAllTransactions();

        validationChain.validate(request, opAccount, transactions, violations);

        if (!violations.isEmpty()) {
            throw new ViolationException(opAccount.orElse(null), violations);
        }

        transactionGateway.createTransaction(request);

        if (opAccount.isPresent()) {
            return accountGateway.updateAccount(
                    new Account(
                            opAccount.get().isActiveCard(),
                            opAccount.get().getAvailableLimit().subtract(request.getAmount())
                    )
            );
        } else {
            return new Account(false, BigDecimal.ZERO);
        }
    }
}
