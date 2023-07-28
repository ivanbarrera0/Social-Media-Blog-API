package Service;

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

    /**
     * 
     * This method takes an account and first checks if the account username
     * exists with another method, username is empty or the password length 
     * is too short. Then the account that is inserted to the database is returned.
     * 
     * @param account
     * @return
     */
    public Account registerAccount(Account account) {

        int check = accountDAO.countAccountsByUsername(account.getUsername()); 

        if(check > 0 || account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            return null;
        }
        else {
            return accountDAO.registerAccount(account);
        }
    }

    /**
     * 
     * This method takes the username and password to check if the
     * account exists with these two parameters. If so the account
     * is returned but this time with its account_id.
     * 
     * @param username
     * @param password
     * @return
     */
    public Account loginAccount(String username, String password) {

        return accountDAO.loginAccount(username, password);
    }
}
