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

    /**
     * This method retrieve all the messages in the database 
     * and return a list comprised of all the messages.
     * 
     * @return list of messages
     */
    public List<Message> retrieveAllMessages() {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {

            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);                
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
        
    }

    /**
     * This method executes a sql preparedstatement to retrieve all the messages
     * based on user id.
     * 
     * @param id
     * @return list of messages
     */
    public List<Message> retrieveAllMessagesFromUserId(int id) {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {

            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    /**
     * This method executes a sql preparedstatement to update a message based 
     * on its message id. Then returns the updated message by using the 
     * retrieveMessageById method.
     * 
     * @param message_text
     * @param id
     * @return message
     */
    public Message updateMessageById(String message_text, int id) {

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, id);
            
            preparedStatement.executeUpdate();


            Message updatedMessage  = retrieveMessageById(id);

            return updatedMessage;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * This method executes a sql preparedstatement to retrieve a message based 
     * on its message id.
     * 
     * @param id
     * @return message
     */
    public Message retrieveMessageById(int id) {

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                rs.getString("message_text"), rs.getLong("time_posted_epoch"));

                return message;
            }
            
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * This method executes a sql preparedstatement that deletes a message
     * based on its message id. Then returns the deleted message by using
     * the retrieveMessageById method.
     * 
     * @param id
     * @return message
     */
    public Message deleteMessageById(int id) {

        Connection connection = ConnectionUtil.getConnection();
        Message returnedMessage = retrieveMessageById(id);

        try {

            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();

            return returnedMessage;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * This method executes a sql preparedstatement that inserts
     * a new message into the database. Then returns the new message.
     * 
     * @param message
     * @return message 
     */
    public Message createMessage(Message message) {

        Connection connection = ConnectionUtil.getConnection();

        try {

            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

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
