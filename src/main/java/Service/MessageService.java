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

    public List<Message> getAllMessagesById(int id) {
        return messageDAO.retrieveAllMessagesFromId(id);
    }

    public Message getMessageByItsId(int id) {
        return messageDAO.retrieveMessageById(id);
    }

    public Message updateMessage(Message message, int id) {
        return messageDAO.updateMessageById(message, id);
    }

    public void deleteMessageById(int id) {
        messageDAO.deleteMessageById(id);
    }

    public Message createNewMessage(Message message) {
        return messageDAO.createMessage(message);
    }
}
