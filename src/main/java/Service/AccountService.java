package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account){
        if(account.username != "" && account.password.length() >= 4){
            return accountDAO.insertAccount(account);
        }
            return null;
    }

    public Account Login(Account account) {
        if(account.username == account.getUsername() && account.password == account.getPassword()){
            return accountDAO.Login(account);
        }
        return null;
    }

    
}




