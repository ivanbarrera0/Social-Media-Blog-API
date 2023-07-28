package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    /**
     * This method executes a SQL query to insert an account into the database 
     * then returns the inserted account.
     * 
     * @param account
     * @return new account
     */
    public Account registerAccount(Account account) {

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "INSERT INTO account(username, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * This method checks if an account exists if its 
     * username exists and its corresponding password is also 
     * present in the account. If so the method returns
     * the method with its account_id included.
     * 
     * @param username
     * @param password
     * @return message(with account_id)
     */
    public Account loginAccount(String username, String password) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"), 
                    rs.getString("username"),
                    rs.getString("password"));
                
                    return account;
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * This method returns the number of accounts that have the 
     * username provided. It is used to check if an account already
     * exists because no accounts should have the same username
     * because it has a unique constraint.
     * 
     * @param username
     * @return count of the same usernames
     */
    public int countAccountsByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
    
        try {
            String sql = "SELECT COUNT(*) FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
    
            while(rs.next()) {
                int count = rs.getInt(1);
                return count;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return 0; 
    }
    
}