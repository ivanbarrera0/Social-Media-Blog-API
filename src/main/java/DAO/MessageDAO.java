package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    public List<Message> retrieveAllMessages() {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {

            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Message message = new Message(rs.getInt("account_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);                
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
        
    }

    public List<Message> retrieveAllMessagesFromId() {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {

            String sql = "SELECT * FROM message WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Message message = new Message(rs.getInt("account_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public void updateMessageById(Message message) {

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message.getMessage_id());
            
            preparedStatement.executeQuery();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Message retrieveMessageById() {

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Message message = new Message(rs.getInt("account_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));

                return message;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // Return type can be changed

    public void deleteMessageById() {

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Message createMessage(Message message) {

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeQuery();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getInt(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}