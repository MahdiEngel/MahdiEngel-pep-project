import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {


    public Account inseAccount(Account account){
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
}



