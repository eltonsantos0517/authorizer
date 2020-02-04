package br.com.authorizer.transaction.usecase;

import java.math.BigDecimal;

public class Transaction {

    private BigDecimal amount;
    private String merchant;
    private String time;

    public Transaction(BigDecimal amount, String merchant, String time) {
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
        private BigDecimal amount;
        private String merchant;
        private String time;

        private Builder() {
        }

        public static Builder aTransaction() {
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

        public Transaction build() {
            return new Transaction(amount, merchant, time);
        }
    }
}
