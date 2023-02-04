package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {


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
    public Account loginCheck(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet resultset = preparedStatement.executeQuery();
            if(resultset.next()){
                Account login = new Account(
                    resultset.getInt(account.account_id),
                    resultset.getString(account.username),
                    resultset.getString(account.password)
                );
            return login;
        }
    }catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return null;

}
    
}


