package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // Registers a new account to the database
    public Account registerAccount(Account account) {

        //if(accountDAO.checkAccountExists(account.getUsername()) != null) {
        //    return null;
        //}

        if(accountDAO.countAccountsByUsername(account.getUsername()) != 0) {
            return null;
        }

        return accountDAO.registerAccount(account);
    }

    public Account loginAccount(String username, String password) {

        return accountDAO.loginAccount(username, password);
    }
}
