package br.com.authorizer.account.usecase.validation;

public interface AccountValidationChainFactory {

    AccountValidator getChain();
}
