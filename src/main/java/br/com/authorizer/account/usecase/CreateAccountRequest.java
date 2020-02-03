package br.com.authorizer.account.usecase;

import java.math.BigDecimal;

public class CreateAccountRequest {

    private boolean activeCard;
    private BigDecimal availableLimit;

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

        public static Builder aRequest() {
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

        public CreateAccountRequest build() {
            CreateAccountRequest createAccountRequest = new CreateAccountRequest();
            createAccountRequest.availableLimit = this.availableLimit;
            createAccountRequest.activeCard = this.activeCard;
            return createAccountRequest;
        }
    }
}
