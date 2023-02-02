package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {


    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUE (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int generate_account_id = (int) resultSet.getLong(1);
                return new Account(generate_account_id, account.getUsername(), account.getPassword());
            }
    } catch (SQLException e){
        System.out.println(e.getMessage());
    }
    return null;
}
    public Account Login(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Account login = new Account(
                    resultSet.getInt(account.account_id),
                    resultSet.getString(account.username),
                    resultSet.getString(account.password)
                );
            return login;
        }
    }catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return null;

}
}


