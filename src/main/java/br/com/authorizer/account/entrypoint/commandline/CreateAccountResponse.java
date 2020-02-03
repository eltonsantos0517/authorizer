package br.com.authorizer.account.entrypoint.commandline;

import br.com.authorizer.account.usecase.AccountViolation;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreateAccountResponse {

    private Account account;
    private List<String> violations;

    public CreateAccountResponse(boolean activeCard, BigDecimal availableLimit, List<String> violations) {

        this.account = new Account(activeCard, availableLimit);
        this.violations = violations;
    }

    public Account getAccount() {
        return account;
    }

    public List<String> getViolations() {
        return violations;
    }

    public static class Account {

        @SerializedName("active-card")
        private boolean activeCard;

        @SerializedName("available-limit")
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

    public static final class Builder {
        private boolean activeCard;
        private BigDecimal availableLimit;
        private List<String> violations;

        private Builder() {
        }

        public static Builder aResponse() {
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

        public Builder withViolations(List<AccountViolation> violations) {
            this.violations = Optional.ofNullable(violations).orElse(new ArrayList<>())
                    .stream()
                    .map(AccountViolation::getId)
                    .collect(Collectors.toList());
            return this;
        }

        public CreateAccountResponse build() {
            return new CreateAccountResponse(this.activeCard, this.availableLimit, this.violations);
        }
    }
}
