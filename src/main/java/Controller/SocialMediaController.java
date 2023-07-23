package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this::messageHandler);
        app.get("/messages/messageId", this::retrieveMessageHandler); // Path may need to be changed
        app.delete("/messages/messageId", this::deleteeMessageHandler); // Path may also be changed
        app.patch("/messages/messageId", this::updateMessageHandler); // as well
        app.get("accounts/accountsId/messages", this::retrieveAllMessagesFromUserHandler); // as well
        //app.start(8080);

        return app;
    }

    // Registers user
    private void registerUserHandler(Context context) {
        context.json("sample text");
    }

    // Verify the user
    private void loginUserHandler(Context context) {
        context.json("sample text");
    }

    private void messageHandler(Context context) {
        context.json("sample text");
    }

    private void retrieveMessageHandler(Context context) {
        context.json("sample text");
    }

    private void deleteeMessageHandler(Context context) {
        context.json("sample text");
    }

    private void updateMessageHandler(Context context) {
        context.json("sample text");
    }

    private void retrieveAllMessagesFromUserHandler(Context context) {
        context.json("sample text");
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}