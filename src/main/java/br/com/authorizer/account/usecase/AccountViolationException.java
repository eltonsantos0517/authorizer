package br.com.authorizer.account.usecase;

import java.util.List;

public class AccountViolationException extends Exception {

    private Account account;
    private List<AccountViolation> violations;

    public AccountViolationException(Account account, List<AccountViolation> violations) {
        super();
        this.account = account;
        this.violations = violations;
    }

    public Account getAccount() {
        return account;
    }

    public List<AccountViolation> getViolations() {
        return violations;
    }
}
