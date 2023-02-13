package Service;


import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

/* This code adds a new account to the database. It takes an "Account" object as an argument and checks if the username 
is not empty and the password length is at least 4 characters. If these conditions are met, it returns the result of 
the "accountDAO.insertAccount(account)" method. If the conditions are not met, the method returns "null". */

    public Account addAccount(Account account){
        if(account.username != "" && account.password.length() >= 4){  //&& !accountDAO.usernameExists(account.username)){
            return accountDAO.insertAccount(account);
        }
            return null;
    }

/* This method is checking if an account with the given username and password exists in the database. 
The method takes an "Account" object as an argument and checks if the username and password provided are equal to the username
and password of the provided "Account" object. If they match, the method calls another method, "accountDAO.loginCheck(account)"
to check the existence of the account in the database. If the account exists, the method returns the account. 
If there is no matching account, the method returns "null". If the provided username and password do not match the username 
and password of the "Account" object, the method will returns "null". */

    public Account loginCheck(Account account) {
         if(account.username == account.getUsername() && account.password == account.getPassword()){
            return accountDAO.loginCheck(account);
        }
        return null;
 
        }
    }

    





