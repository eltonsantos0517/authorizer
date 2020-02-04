package br.com.authorizer.transaction.usecase;

import java.math.BigDecimal;

public class CreateTransactionRequest {

    public BigDecimal amount;
    public String merchant;
    public String time;

    public CreateTransactionRequest(BigDecimal amount, String merchant, String time) {
        this.amount = amount;
        this.merchant = merchant;
        this.time = time;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getTime() {
        return time;
    }


    public static final class Builder {
        public BigDecimal amount;
        public String merchant;
        public String time;

        private Builder() {
        }

        public static Builder aRequest() {
            return new Builder();
        }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withMerchant(String merchant) {
            this.merchant = merchant;
            return this;
        }

        public Builder withTime(String time) {
            this.time = time;
            return this;
        }

        public CreateTransactionRequest build() {
            return new CreateTransactionRequest(amount, merchant, time);
        }
    }
}
