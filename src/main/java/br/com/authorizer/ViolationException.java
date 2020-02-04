package br.com.authorizer;

import br.com.authorizer.account.usecase.Account;

import java.util.List;

public class ViolationException extends Exception {

    private Account account;
    private List<Violation> violations;

    public ViolationException(Account account, List<Violation> violations) {
        super();
        this.account = account;
        this.violations = violations;
    }

    public Account getAccount() {
        return account;
    }

    public List<Violation> getViolations() {
        return violations;
    }
}
