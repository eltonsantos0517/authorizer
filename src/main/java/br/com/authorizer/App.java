/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package br.com.authorizer;

import br.com.authorizer.account.entrypoint.commandline.AccountCommandLineController;
import br.com.authorizer.account.entrypoint.commandline.CreateAccountResponse;
import br.com.authorizer.account.gateway.AccountRepository;
import br.com.authorizer.account.gateway.client.AccountInMemoryDatabase;
import br.com.authorizer.account.usecase.CreateAccountUseCase;
import br.com.authorizer.account.usecase.validation.AccountValidationChain;
import br.com.authorizer.account.usecase.validation.DefaultAccountValidationChainFactory;
import br.com.authorizer.transaction.entrypoint.commandline.CreateTransactionResponse;
import br.com.authorizer.transaction.entrypoint.commandline.TransactionCommandLineController;
import br.com.authorizer.transaction.gateway.TransactionRepository;
import br.com.authorizer.transaction.gateway.client.TransactionInMemoryDatabase;
import br.com.authorizer.transaction.usecase.CreateTransactionUseCase;
import br.com.authorizer.transaction.usecase.validation.DefaultTransactionValidationFactory;
import br.com.authorizer.transaction.usecase.validation.TransactionValidationChain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.*;
import java.math.BigDecimal;

public class App {

    public static void main(String[] args) throws Exception {
        run(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
    }

    public static void run(Reader in, Writer out) throws Exception {
        BufferedReader r = new BufferedReader(in);
        PrintWriter w = new PrintWriter(out);
        Gson g = new GsonBuilder().serializeNulls().create();
        String line;

        AccountInMemoryDatabase accountInMemoryDatabase = new AccountInMemoryDatabase();
        AccountCommandLineController accountController = new AccountCommandLineController(
                new CreateAccountUseCase(
                        new AccountRepository(
                                accountInMemoryDatabase
                        ),
                        new AccountValidationChain(
                                new DefaultAccountValidationChainFactory()
                        )
                )
        );
        TransactionCommandLineController transactionController = new TransactionCommandLineController(
                new CreateTransactionUseCase(
                        new AccountRepository(
                                accountInMemoryDatabase
                        ),
                        new TransactionRepository(
                                new TransactionInMemoryDatabase()
                        ),
                        new TransactionValidationChain(
                                new DefaultTransactionValidationFactory()
                        )
                )
        );

        while ((line = r.readLine()) != null) {
            Request request = g.fromJson(line, Request.class);
            if (request.account != null) {
                CreateAccountResponse response = accountController.createAccount(request.account);
                w.println(g.toJson(response));
                w.flush();

            } else if (request.transaction != null) {
                CreateTransactionResponse response = transactionController.createTransaction(request.transaction);
                w.println(g.toJson(response));
                w.flush();
            }
        }
    }

    public static class Account {
        @SerializedName("active-card")
        private boolean activeCard;

        @SerializedName("available-limit")
        private BigDecimal availableLimit;

        public boolean isActiveCard() {
            return activeCard;
        }

        public void setActiveCard(boolean activeCard) {
            this.activeCard = activeCard;
        }

        public BigDecimal getAvailableLimit() {
            return availableLimit;
        }

        public void setAvailableLimit(BigDecimal availableLimit) {
            this.availableLimit = availableLimit;
        }
    }

    public static class Transaction {
        private BigDecimal amount;
        private String merchant;
        private String time;

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getMerchant() {
            return merchant;
        }

        public void setMerchant(String merchant) {
            this.merchant = merchant;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class Request {
        private Account account;
        private Transaction transaction;

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public Transaction getTransaction() {
            return transaction;
        }

        public void setTransaction(Transaction transaction) {
            this.transaction = transaction;
        }
    }

}
