package br.com.authorizer.transaction.usecase.validation;

public interface TransactionValidationChainFactory {

    TransactionValidation getChain();

}
