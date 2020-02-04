package br.com.authorizer.transaction.entrypoint.commandline;

import br.com.authorizer.Violation;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreateTransactionResponse {

    private AccountResponse account;
    private List<String> violations;

    public CreateTransactionResponse(AccountResponse account, List<String> violations) {

        this.account = account;
        this.violations = violations;
    }

    public AccountResponse getAccount() {
        return account;
    }

    public List<String> getViolations() {
        return violations;
    }

    public static final class Builder {
        private AccountResponse account;
        private List<String> violations;

        private Builder() {
        }

        public static Builder aResponse() {
            return new Builder();
        }

        public Builder withAccountResponse(AccountResponse account) {
            this.account = account;
            return this;
        }

        public Builder withViolations(List<Violation> violations) {
            this.violations = Optional.ofNullable(violations).orElse(new ArrayList<>())
                    .stream()
                    .map(Violation::getId)
                    .collect(Collectors.toList());
            return this;
        }

        public CreateTransactionResponse build() {
            return new CreateTransactionResponse(this.account, this.violations);
        }
    }

    public static class AccountResponse {

        @SerializedName("active-card")
        private boolean activeCard;

        @SerializedName("available-limit")
        private BigDecimal availableLimit;

        public AccountResponse(boolean activeCard, BigDecimal availableLimit) {
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

            public AccountResponse build() {
                return new AccountResponse(this.activeCard, this.availableLimit);
            }
        }


    }
}
