package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.Statement;



public class MessageDAO {
    
/* The method creates a SQL query to retrieve all data from the message table and prepares a statement for execution.
The statement is executed, and the result is stored in a ResultSet object.
The code then iterates over the result set, and for each row, it creates a Message object using the data from 
the current row, and adds it to the messages list. Finally, the method returns the messages list. */

    public List<Message> getAllMassages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

/* The method creates a PreparedStatement and sets the values for posted_by, message_text, and time_posted_epoch with 
the setInt and setString methods. It then executes the update and gets the generated message ID from the result set.
If the result set contains a generated message ID, the method creates a new Message object with the generated ID 
and other information, and returns it. If there is no generated message ID, the method returns null. */

    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int generated_message_id = (int) resultSet.getInt("message_id");
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
            //return message;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

/* The method getMessagesById retrieves a message with a specific message_id from the database. 
The SQL query selects the message with the matching message_id. The query is executed using a prepared statement
and the message_id is set as the parameter. If a message is found, it is returned as a Message object, otherwise, 
the method returns null. */

    public Message getMessagesById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    Message message = new Message(resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch"));

                return message;
                }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }
    
/* This code retrieves a list of messages from a database table "message" based on the account id of the user who posted 
the messages. The code then prepares an SQL statement to select all messages posted by a given account id. 
The account id is set as a parameter in the prepared statement using the preparedStatement.setInt(1, accountId) method. 
The code then executes the prepared statement using the preparedStatement.executeQuery() method and retrieves the result set.
The result set is iterated and for each row, a message object is created using the values from the result set. 
The message object is added to a list of messages, which is returned at the end of the code. */

    public List<Message> getMassagesByAccountId(int accountId){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Message message = new Message(resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

/* This code updates a message in the database based on a given message ID. The code uses a "PATCH" method to update 
the "message_text" field of a message in the "message" table. 
The SQL logic to update the message is written in a prepared statement, which sets the new message text to 
the given "message.getMessage_text()" value, and the message ID to the given "messageID". 
The update is executed using the "preparedStatement.executeUpdate()" method. Finally, the updated message is retrieved
using the "getMessagesById(messageID)" method and returned as the result. */

    public Message patchMessageById(Message message, int messageID){
        Connection connection = ConnectionUtil.getConnection();
        try{
            //Write SQL logic here
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, messageID);

            preparedStatement.executeUpdate();
            return getMessagesById(messageID);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

/* This code defines a deleteMessageById method, which deletes a message from the "message" database table based on 
the provided messageID. Then it writes an SQL query in the form of a prepared statement, which deletes a message where 
the message_id column matches the provided messageID. The prepared statement is executed using 
preparedStatement.executeUpdate(). The method returns null as it does not need to return anything, since it only performs 
a delete operation. */

    public Message deleteMessageById(int messageID){
        Connection connection = ConnectionUtil.getConnection();
        try{
         //Write SQL logic here
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
             preparedStatement.setInt(1, messageID);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

    
