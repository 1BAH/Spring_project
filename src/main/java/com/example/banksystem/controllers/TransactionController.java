package com.example.banksystem.controllers;

import com.example.banksystem.models.Account;
import com.example.banksystem.models.Client;
import com.example.banksystem.models.Transaction;
import com.example.banksystem.repositories.AccountRepository;
import com.example.banksystem.repositories.ClientRepository;
import com.example.banksystem.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller for transactions from one account to another one
 */
@Controller
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Page /transactions - the table of all transactions from current user
     * @return transactions template
     */
    @GetMapping("/transactions")
    public String transactionsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        List<Account> accounts = currentClient.getAccounts();
        Iterable<Transaction> transactions = transactionRepository.findAll();

        List<Transaction> currentClientTransactions = new ArrayList<>();

        for (Transaction transaction: transactions) {
            if (accounts.contains(transaction.getAccountFrom())) {
                currentClientTransactions.add(transaction);
            }
        }

        model.addAttribute("user", currentClient);
        model.addAttribute("transactions", currentClientTransactions);
        model.addAttribute("title", "Transactions");
        return "transactions/transactions";
    }

    /**
     * Start transaction
     * @return redirects to step-1 page
     */
    @GetMapping("/transactions/make")
    public String startTransaction() {
        return "redirect:/transactions/make/step-1";
    }

    /**
     * <b>Transaction creation - step #1</b>
     * <br>
     * The choice of the account where transaction will be made from
     * The choice of strategy:
     * <br>
     * - to user's personal accounts
     * <br>
     * - to other users' accounts
     * @return transaction1 template
     */
    @GetMapping("/transactions/make/step-1")
    public String step1(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }
        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("user", currentClient);
        model.addAttribute("accounts", accounts);
        model.addAttribute("title", "Step 1");
        return "transactions/transactions1";
    }

    /**
     * Switch between strategies mentioned before
     * @param accountFrom
     * @param self strategy
     * @return redirects to step-2 or step-2-self
     */
    @GetMapping("/transactions/make/step-1/form")
    public String step1results(@RequestParam long accountFrom, @RequestParam boolean self) {
        if (self) {
            return "redirect:/transactions/make/step-2-self/" + accountFrom;
        } else {
            return "redirect:/transactions/make/step-2/" + accountFrom;
        }
    }

    /**
     * <b>Transaction creation step #2 self</b><br>
     * Choice of account
     * @param fromId
     * @return transaction2-self template
     */
    @GetMapping("/transactions/make/step-2-self/{fromId}")
    public String step2self(Model model, @PathVariable(value = "fromId") long fromId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }
        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("user", currentClient);
        model.addAttribute("accounts", accounts);
        model.addAttribute("title", "Step 2");
        model.addAttribute("chosenAcc", fromId);
        return "transactions/transactions2-self";
    }

    /**
     * <b>Transaction creation step #2</b><br>
     * Choice of account
     * @param fromId
     * @return transaction2 template
     */
    @GetMapping("/transactions/make/step-2/{fromId}")
    public String step2(Model model, @PathVariable(value = "fromId") long fromId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }
        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Step 2");
        model.addAttribute("chosenAcc", fromId);
        return "transactions/transactions2";
    }


    /**
     * Redirection to step #3
     * @param fromId
     * @param accountTo
     * @return redirects to page /transactions/make/step-3/{fromId}/{accountTo}
     */
    @GetMapping("/transactions/make/step-2-self/{fromId}/form")
    public String step2selfResults(@PathVariable(value = "fromId") long fromId, @RequestParam long accountTo) {
        return "redirect:/transactions/make/step-3/" + fromId + "/" + accountTo;
    }

    /**
     * Redirection to step #3
     * @param fromId
     * @param accountTo
     * @return redirects to page /transactions/make/step-3/{fromId}/{accountTo}
     */
    @GetMapping("/transactions/make/step-2/{fromId}/form")
    public String step2results(@PathVariable(value = "fromId") long fromId, @RequestParam long accountTo) {
        return "redirect:/transactions/make/step-3/" + fromId + "/" + accountTo;
    }

    /**
     * <b>Transaction creation - step #3</b><br>
     * Input of the amount
     * @param fromId
     * @param toId
     * @return transactions3 template
     */
    @GetMapping("/transactions/make/step-3/{fromId}/{toId}")
    public String step3(Model model, @PathVariable(value = "fromId") long fromId, @PathVariable(value = "toId") long toId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        List<Account> accounts = currentClient.getAccounts();

        Account accFrom = accountRepository.findById(fromId).get();
        Account accTo = accountRepository.findById(toId).get();

        model.addAttribute("user", currentClient);
        model.addAttribute("accounts", accounts);
        model.addAttribute("title", "Step 3");
        model.addAttribute("fromAcc", accFrom);
        model.addAttribute("toAcc", accTo);
        return "transactions/transactions3";
    }

    /**
     * <b>Transaction creation - step #4</b><br>
     * Confirmation
     * @param amount amount of money
     * @param fromId money will be transferred from account
     * @param toId money will be transferred to account
     * @return transaction4 template
     */
    @GetMapping("/transactions/make/step-4/{fromId}/{toId}/form")
    public String step4(@RequestParam BigDecimal amount, @PathVariable(value = "fromId") long fromId, @PathVariable(value = "toId") long toId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());

        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }

        Account accFrom = accountRepository.findById(fromId).get();
        Account accTo = accountRepository.findById(toId).get();

        float commision = 0;
        boolean colored = true;

        model.addAttribute("user", currentClient);
        model.addAttribute("title", "Step 4");
        model.addAttribute("fromAcc", accFrom);
        model.addAttribute("toAcc", accTo);
        model.addAttribute("amount", amount);

        if (accFrom.getBank().getId() != accTo.getBank().getId()) {
            commision = accFrom.getBank().getPercentage();
            colored = false;
        }

        model.addAttribute("colored", colored);
        model.addAttribute("commision", commision);
        model.addAttribute("realAmount", amount.multiply(new BigDecimal(1 + commision / 100)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        return "transactions/transactions4";
    }

    /**
     * Completes the transaction and redirects to transactions page or redirects to an error page if there are not enough money on account.
     * @param amount amount of money
     * @param fromId money will be transferred from account
     * @param toId money will be transferred to account
     * @param withoutCommission false if the commission is provided otherwise true
     * @return redirects to transactions page or redirects to an error page if there are not enough money on account
     */
    @GetMapping("/transactions/make/{fromId}/{toId}/{amount}/{withCommission}")
    public String makeTransaction(@PathVariable(value = "fromId") long fromId, @PathVariable(value = "toId") long toId, @PathVariable(value = "amount") BigDecimal amount, @PathVariable(value = "withCommission") boolean withoutCommission) {
        Account accFrom = accountRepository.findById(fromId).get();
        Account accTo = accountRepository.findById(toId).get();

        if (accFrom.withdrawMoney(amount, !withoutCommission)) {
            accTo.setAlert((byte) 1);
            accTo.putMoney(amount);


            accountRepository.save(accFrom);


            Transaction transaction = new Transaction(accFrom, accTo, amount);
            transactionRepository.save(transaction);

            accTo.setLastTransactionId(transaction.getId());
            accountRepository.save(accTo);

            return "redirect:/transactions";
        } else {
            return "redirect:/transactions/transaction-error";
        }
    }

    /**
     * Page /transactions/transsaction-error - an error page for transactions
     * @return unsuccessful template
     */
    @GetMapping("/transactions/transaction-error")
    public String transactionError(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }
        List<Account> accounts = currentClient.getAccounts();
        model.addAttribute("user", currentClient);
        model.addAttribute("accounts", accounts);
        model.addAttribute("title", "ERROR");
        return "operations/unsuccessful";
    }

    /**
     * Shows info about chosen transaction or rights error
     * @param id transaction id
     * @return info template if the transaction is from current user or to him otherwise restricted template
     */
    @GetMapping("/transaction/{id}")
    public String transactionInfo(Model model, @PathVariable(name = "id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Client currentClient = clientRepository.findByPassport(authentication.getName());
        if (Objects.isNull(currentClient)) {
            model.addAttribute("title", "403 FORBIDDEN");
            return "errors/403-cl";
        }
        model.addAttribute("user", currentClient);

        Transaction transaction = transactionRepository.findById(id).get();

        if (currentClient.getId() == transaction.getAccountFrom().getHolder().getId()
                || currentClient.getId() == transaction.getAccountTo().getHolder().getId()) {
            model.addAttribute("title", "Transaction @" + id);
            model.addAttribute("transaction", transaction);
            return "transactions/info";
        } else {
            model.addAttribute("title", "ERROR");
            return "errors/restricted";
        }
    }
}
