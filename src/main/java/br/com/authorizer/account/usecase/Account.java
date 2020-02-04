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


    public static final class Builder {
        private boolean activeCard;
        private BigDecimal availableLimit;

        private Builder() {
        }

        public static Builder anAccount() {
            return new Builder();
        }

        public Builder withActiveCard(boolean activeCard) {
            this.activeCard = activeCard;
            return this;
        }

        public Builder withAvailableLimit(BigDecimal availableLimit) {
            this.availableLimit = availableLimit;
            return this;
        }

        public Account build() {
            return new Account(activeCard, availableLimit);
        }
    }
}
