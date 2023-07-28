package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * 
     * This method executes another method to retrieve
     * all messages in the database in a list.
     * 
     * @return a list of the messages
     */
    public List<Message> getAllMessages() {
        return messageDAO.retrieveAllMessages();
    }

    /**
     * 
     * This method calls another method that will get all
     * messages based on user id.
     * 
     * @param id
     * @return a list of messages
     */
    public List<Message> getAllMessagesByUserId(int id) {
        return messageDAO.retrieveAllMessagesFromUserId(id);
    }

    /**
     * 
     * This method gets a message based on its id.
     * 
     * @param id
     * @return message 
     */
    public Message getMessageByItsId(int id) {
        return messageDAO.retrieveMessageById(id);
    }

    /**
     * This method calls another method to update the message. It checks 
     * if the message_text is either empty or too long.
     * 
     * @param message
     * @param id
     * @return updated message
     */
    public Message updateMessage(Message message, int id) {

        if(message.getMessage_text().isEmpty() || message.getMessage_text().length() > 254) {
            return null;
        }

        return messageDAO.updateMessageById(message.getMessage_text(), id);
    }

    /**
     * This method calls another method that deletes a message 
     * based on its id. 
     * 
     * @param id
     * @return deleted message
     */
    public Message deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }

    /**
     * This method calls another method to create a message. It first
     * checks if the message_text is empty or too short.
     * 
     * @param message
     * @return new message
     */
    public Message createNewMessage(Message message) {
        if(message.message_text.isEmpty() || message.message_text.length() > 254) {
            return null;
        }
        return messageDAO.createMessage(message);
    }
}
