package br.com.authorizer.account.usecase;

public enum AccountViolation {

    ACCOUNT_ALREADY_INITIALIZED("account-already-initialized");

    private String id;

    AccountViolation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
