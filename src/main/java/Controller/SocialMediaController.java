package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerNewUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::retrieveAllMessagesHandler);
        app.get("/messages/{message_id}", this::retrieveMessageByMessageIdHandler); 
        app.delete("/messages/{message_id}", this::deleteMessageByMessageIdHandler); 
        app.patch("/messages/{message_id}", this::updateMessageByMessageIdHandler); 
        app.get("accounts/{accounts_id}/messages", this::retrieveAllMessagesByUserIdHandler); 

        return app;
    }

    /**
     * This method is used to create a new user 
     * and add their generated id, username and password
     * to the database.
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void registerNewUserHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addAccount = accountService.registerAccount(account);

        if(addAccount == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(addAccount));
        }
    }

    /**
     * This method takes request body and creates an account object.
     * Then create another account that will be checked to see if it exists
     * based on its username and password
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void loginUserHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
        if(loginAccount == null) {
            context.status(401);
        } else {
            context.json(mapper.writeValueAsString(loginAccount));
        }
    }

    /**
     * This method creates a message object and inserts it into the database.
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void createMessageHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addMessage = messageService.createNewMessage(message);
        if(addMessage == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(addMessage));
        }
    }

    /**
     * This method retrieves all the messages in the database 
     * into a list that is then turned to JSON representation of the list
     * 
     * @param context
     */
    private void retrieveAllMessagesHandler(Context context) {
        context.json(messageService.getAllMessages());
    }

    /**
     * This method retrieves a message based on its message_id.
     * The id is taken from the endpoint.
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void retrieveMessageByMessageIdHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message targetMessage = messageService.getMessageByItsId(message_id);
        
        if(targetMessage == null) {
            context.status(200);
        } else {
            context.json(mapper.writeValueAsString(targetMessage));
        }
    }

    /**
     * This method deletes a message based on its message_id.
     * The id is taken from the endpoint.
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void deleteMessageByMessageIdHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(message_id);
        if(deletedMessage == null) {
            context.status(200);
        } else {
            context.json(mapper.writeValueAsString(deletedMessage));
        }
    }

    /**
     * This method updates the message_text of a message based on
     * its message_id. The id is taken from the endpoint and the
     * message_text is taken from the response body.
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void updateMessageByMessageIdHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Message message = context.bodyAsClass(Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message, message_id);
        if(updatedMessage == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(updatedMessage));
        }
    }

    /**
     * This method retrieves all messages from a user based 
     * on the user's id. The id is taken from the endpoint.
     * 
     * @param context
     * @throws JsonProcessingException
     */
    private void retrieveAllMessagesByUserIdHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        int account_id = Integer.parseInt(context.pathParam("accounts_id"));

        context.json(mapper.writeValueAsString(messageService.getAllMessagesByMessageId(account_id)));
    }

}