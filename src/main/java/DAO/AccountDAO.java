package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    // Add the condition if the account already exists or the password is too short

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

    public Account loginAccount(String username, String password) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM book WHERE username = ? AND password = ?;";
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
}