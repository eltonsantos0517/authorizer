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
}
