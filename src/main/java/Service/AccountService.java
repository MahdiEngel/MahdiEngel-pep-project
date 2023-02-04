package Service;


import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account){
        if(account.username != "" && account.password.length() >= 4) {//&& !accountDAO.usernameExists(account.username)){
            return accountDAO.insertAccount(account);
        }
            return null;
    }

  /*public Account login(String username, String password){
        Account account = accountDAO.getAccountByUsername(username);
        if(account != null && account.password.equals(password)){
        return account;
        }
        return null;
        } */

    public Account loginCheck(Account account) {
        if(account.username == account.getUsername() && account.password == account.getPassword()){
            return accountDAO.loginCheck(account);
        }
        return null;
    }

    
}




