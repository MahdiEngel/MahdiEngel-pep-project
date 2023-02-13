package Service;

import Model.Message;

import java.util.List;

import DAO.MessageDAO;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }


 public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    
/* It is using the "messageDAO" object to call its "getAllMassages()" method, to retrieve all the messages stored in 
the database and return them as a List. The result of this method call is then returned by the "getAllMassages()" method 
as its final result. */

public List<Message> getAllMassages() {
    return messageDAO.getAllMassages();
}

/* The method takes in a "Message" object as an argument and checks if the message text is not empty 
and less than 255 characters in length. If the message text satisfies these conditions, the method inserts
the message into the database using the "messageDAO.insertMessage(message)" statement and returns the 
message object. If the message text is either empty or longer than 255 characters, 
the method returns "null". */

public Message addMessage(Message message){
    if(message.message_text != "" && message.message_text.length() < 255){
        return messageDAO.insertMessage(message);
    }
        return null;
}

/* The method takes in an integer "id" as an argument and returns the message object that has 
the same id by calling the "messageDAO.getMessageById(id)" statement. */

public Message getMessageById(int id){
    return messageDAO.getMessagesById(id);
}

/* The method takes in an integer "accountId" as an argument and returns a list of message objects 
associated with the given account id by calling the "messageDAO.getMassagesByAccountId(accountId)" 
statement. */

public List<Message> getAllMassagesByAccountId(int accountId){
    return messageDAO.getMassagesByAccountId(accountId);
}

/* The method takes in two arguments: a "Message" object and an integer "messageID". 
The method first checks if the message text is either null or empty, or if it is longer than 255 characters.
If any of these conditions is true, the method returns "null". If the message text is not null, 
not empty, and within the specified length limit, the method updates the existing message in 
the database with the new data in the "Message" object, using 
the "messageDAO.patchMessageById(message, messageID)" statement and returns the updated message object. */

public Message patchMessageById(Message message, int messageID){
    if (message.message_text == null || message.message_text.isEmpty() || message.message_text.length() > 255){
    
        return null;
    }
    return messageDAO.patchMessageById(message, messageID);
}

/* The method takes in an integer "messageID" as an argument and first retrieves the message object 
associated with the given id by calling the "messageDAO.getMessagesById(messageID)" statement. 
Then, the method deletes the message from the database using the "messageDAO.deleteMessageById(messageID)"
statement. Finally, the method checks if the message was found in the database (i.e., not null) 
and returns the message object. If the message was not found, the method returns "null". */

public Message deleteMessageById(int messageID){
    Message message = messageDAO.getMessagesById(messageID);
    messageDAO.deleteMessageById(messageID);
    if(message == null){
        return null;
    }
    return message;
}

}
