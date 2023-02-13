package Controller;

import java.util.List;

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
public class SocialMediaController{
    AccountService accountService;
    MessageService messageService;


public SocialMediaController(){
    this.accountService = new AccountService();
    this.messageService = new MessageService();
}
/**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
*/
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMassagesByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMassagesByAccountIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        return app;
    }


/* Calls the addAccount method on an AccountService object, passing in the deserialized Account object.
 This method is assumed to add the Account object to a database.
 If the addAccount method returns a non-null value, this is assumed to indicate that the account was successfully
 added, and the code serializes the returned Account object into a JSON response using the ObjectMapper and writes 
 it to the Context response.
 If the addAccount method returns a null value, this is assumed to indicate that there was an error, 
 and the code sets the HTTP status code in the Context response to 400 Bad Request. */

    private void postRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = accountService.addAccount(account);
        if(newAccount != null){
            context.json(mapper.writeValueAsString(newAccount));
        }else{
            context.status(400);
        }
    }

/* Calls the loginCheck method on an AccountService object, passing in the deserialized Account object. 
This method is assumed to verify the authenticity of the user's credentials (e.g. username and password).
If the loginCheck method returns a non-null value, this is assumed to indicate that the user's credentials are valid,
and the code serializes the returned Account object into a JSON response using the ObjectMapper and writes it to 
the Context response.
If the loginCheck method returns a null value, this is assumed to indicate that the user's credentials are invalid,
and the code sets the HTTP status code in the Context response to 401 Unauthorized. */

    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account logIn = accountService.loginCheck(account);
        if(logIn != null){
            context.json(mapper.writeValueAsString(logIn));
        }else {
            context.status(401);
        }
    }

/* Calls the getAllMassages method on a MessageService object. This method is assumed to retrieve a list of 
all Message objects stored in some kind of data storage.
Writes the list of Message objects to the response body of the Context object in JSON format using the json method. */

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMassages();
        context.json(messages);
    }

/* Calls the addMessage method on a MessageService object, passing in the deserialized Message object.
This method is assumed to add the Message object to some sort of storage, such as a database.
If the addMessage method returns a non-null value, this is assumed to indicate that the message was successfully 
added, and the code serializes the returned Message object into a JSON response using the ObjectMapper and writes it
to the Context response.
If the addMessage method returns a null value, this is assumed to indicate that there was an error, and the code 
sets the HTTP status code in the Context response to 400 Bad Request. */

    private void postMessagesHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.addMessage(message);
        if(newMessage != null){
            context.json(mapper.writeValueAsString(newMessage));
        }else{
            context.status(400);
        }
    }

/* The code defines a method in Java that retrieves a message by its ID and returns it as a JSON response. 
The method takes a Context object as an argument and retrieves the message ID from the path parameter. 
The message is then retrieved from the message service and returned in JSON format if it's not null. 
If the message is null, the method sets the HTTP status code to 200. */

    private void getMassagesByIdHandler(Context context){
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageID);
        if(message != null){
            context.json(message);
        }else{
            context.status(200);
        }
    }

/* The account ID is obtained from the path parameter of the context and is parsed into an integer using the 
Integer.parseInt method. The method then calls the messageService.getAllMassagesByAccountId method, 
passing the account ID as a parameter, to retrieve the messages.
The method returns the messages in the JSON format using the context.json method. */

    private void getMassagesByAccountIdHandler(Context context){
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message>messages = messageService.getAllMassagesByAccountId(accountId);
        context.json(messages);
    }


/* This code defines a Java method called patchMessageByIdHandler that updates a message and returns it as a JSON 
response. The method reads the body of the request, maps it to a Message object using ObjectMapper, 
and gets the message ID from the path parameter. The method then calls the messageService to update the message and 
returns it in JSON format if the update was successful. If the update fails, the method sets the HTTP status code 
to 400. */

    private void patchMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message patchMessage = messageService.patchMessageById(message, messageID);
        if(patchMessage != null){
            context.json(mapper.writeValueAsString(patchMessage));
        }else{
            context.status(400);
        }

    }

/* The method first retrieves the message ID from the path parameter of the context using the context.pathParam 
method and parses it into an integer using Integer.parseInt.
The method then calls the messageService.deleteMessageById method, passing the message ID as an argument, 
to delete the message. If the message is not null, the method returns the deleted message in JSON format using the 
context.json method. If the message is null, the method sets the HTTP status code to 200 using the context.status 
method. */

        private void deleteMessageByIdHandler(Context context) throws JsonProcessingException{
            int messageID = Integer.parseInt(context.pathParam("message_id"));
            Message deleteMessage = messageService.deleteMessageById(messageID);
            if(deleteMessage != null){
                context.json(deleteMessage);
            }else{
                context.status(200);
            }
        }
}


