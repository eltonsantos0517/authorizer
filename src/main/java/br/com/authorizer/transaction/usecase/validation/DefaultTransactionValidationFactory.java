package br.com.authorizer.transaction.usecase.validation;

public class DefaultTransactionValidationFactory implements TransactionValidationChainFactory {

    public TransactionValidation getChain() {
        return new AccountNotInitializedTransactionValidation(
                new InsufficientLimitIntervalTransactionValidation(
                        new CardNotActiveTransactionValidation(
                                new HighFrequencySmallIntervalTransactionValidation(
                                        new DoubledTransactionValidation(
                                                null
                                        )
                                )
                        )
                )
        );
    }
}
