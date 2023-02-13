package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {

/* The method creates an SQL INSERT statement to insert a new row into the "account" table with the given username and password.
The method uses a "PreparedStatement" object to execute the SQL statement and set the values for the username and password. 
The method then calls "preparedStatement.executeUpdate()" to execute the SQL statement. After executing the SQL statement, 
the method retrieves the generated account id using the "ResultSet" object and the "resultSet.getLong(1)" statement, 
and creates a new "Account" object with the generated account id, username, and password. The new "Account" 
object is then returned by the method. If any errors occur during the execution, the method will catch the "SQLException" 
and print the error message to the console using "System.out.println(e.getMessage())" before returning "null". */

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int account_id = (int) resultSet.getLong(1);
                return new Account(account_id, account.getUsername(), account.getPassword());
            }
    } catch (SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}

/* This code checks if a user with a given username and password exists in the database. 
If it exists, it returns an Account object with the account information. If it doesn't exist or an error occurs, 
it returns null. */

    public Account loginCheck(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()) {
                int id = resultset.getInt("account_id");
                String usernsame = resultset.getString("username");
                String password = resultset.getString("password");
                Account login = new Account(id, usernsame, password);
                return login;
            }
    }catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return null;

}
    
}


