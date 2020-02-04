package br.com.authorizer;

public enum Violation {

    ACCOUNT_ALREADY_INITIALIZED("account-already-initialized"),
    ACCOUNT_NOT_INITIALIZED("account-not-initialized"),
    INSUFFICIENT_LIMIT("insufficient-limit"),
    CARD_NOT_ACTIVE("card-not-active"),
    HIGH_FREQUENCY_SMALL_INTERVAL("high-frequency-small-interval"),
    DOUBLED_TRANSACTION("doubled-transaction");

    private String id;

    Violation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
