package br.com.authorizer.account.usecase.validation;

public class DefaultAccountValidationChainFactory implements AccountValidationChainFactory {

    public AccountValidator getChain() {
        return new ActiveAccountValidator(null);
    }
}
