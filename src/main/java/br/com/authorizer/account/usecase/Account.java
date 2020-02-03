package br.com.authorizer.account.usecase;

import java.math.BigDecimal;

public class Account {

    private boolean activeCard;
    private BigDecimal availableLimit;

    public Account(boolean activeCard, BigDecimal availableLimit) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
    }

    public boolean isActiveCard() {
        return activeCard;
    }

    public BigDecimal getAvailableLimit() {
        return availableLimit;
    }
}
