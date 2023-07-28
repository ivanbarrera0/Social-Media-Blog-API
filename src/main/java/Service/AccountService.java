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

        int check = accountDAO.countAccountsByUsername(account.getUsername()); 

        if(check > 0 || account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            return null;
        }
        else {
            return accountDAO.registerAccount(account);
        }
    }

    // Logins in account checks if it exists
    public Account loginAccount(String username, String password) {

        return accountDAO.loginAccount(username, password);
    }
}
