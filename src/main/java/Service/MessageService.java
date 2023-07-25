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

    public List<Message> getAllMessages() {
        return messageDAO.retrieveAllMessages();
    }

    // Get all the messages by user id
    public List<Message> getAllMessagesByMessageId(int id) {
        return messageDAO.retrieveAllMessagesFromMessageId(id);
    }

    // Get message by message id
    public Message getMessageByItsId(int id) {
        return messageDAO.retrieveMessageById(id);
    }

    public Message updateMessage(Message message, int id) {
        return messageDAO.retrieveMessageById(id);
    }

    public Message deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }

    public Message createNewMessage(Message message) {
        return messageDAO.createMessage(message);
    }
}
