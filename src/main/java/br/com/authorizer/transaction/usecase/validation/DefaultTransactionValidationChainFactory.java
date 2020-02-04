package br.com.authorizer.transaction.usecase.validation;

public class DefaultTransactionValidationChainFactory implements TransactionValidationChainFactory {

    public TransactionValidator getChain() {
        return new AccountNotInitializedTransactionValidator(
                new InsufficientLimitIntervalTransactionValidator(
                        new CardNotActiveTransactionValidator(
                                new HighFrequencySmallIntervalTransactionValidator(
                                        new DoubledTransactionValidator(
                                                null
                                        )
                                )
                        )
                )
        );
    }
}
