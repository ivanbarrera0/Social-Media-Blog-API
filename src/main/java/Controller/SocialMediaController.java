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
        //app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::retrieveAllMessagesHandler);
        app.get("/messages/{message_id}", this::retrieveMessageByItsIdHandler); 
        app.delete("/messages/{message_id}", this::deleteMessageHandler); 
        app.patch("/messages/{message_id}", this::updateMessageHandler); 
        app.get("accounts/{accounts_id}/messages", this::retrieveAllMessagesFromUserIdHandler); 
        //app.start(8080);

        return app;
    }
    
    // 1
    // Registers user
    // I left off here 
    // get the username and password
    private void registerUserHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addAccount = accountService.registerAccount(account);

        if(addAccount == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(addAccount));
        }
    }

    // 2 
    // Verify the user
    // WORKS
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

    // 3
    // WORKS
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

    // 4 retrieve all messages
    // WORKS
    private void retrieveAllMessagesHandler(Context context) {
        context.json(messageService.getAllMessages());
    }

    // 5 
    // WORKS
    private void retrieveMessageByItsIdHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message targetMessage = messageService.getMessageByItsId(message_id);
        
        if(targetMessage == null) {
            context.status(200);
        } else {
            context.json(mapper.writeValueAsString(targetMessage));
        }
    }

    // 6
    private void deleteMessageHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(message_id);
        if(deletedMessage == null) {
            context.status(200);
        } else {
            context.json(mapper.writeValueAsString(deletedMessage));
        }
    }

    // 7 
    // WORKS
    private void updateMessageHandler(Context context) throws JsonProcessingException {

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

    // 8
    // WORKS
    private void retrieveAllMessagesFromUserIdHandler(Context context) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        int account_id = Integer.parseInt(context.pathParam("accounts_id"));

        context.json(mapper.writeValueAsString(messageService.getAllMessagesByMessageId(account_id)));
    }

}