import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account adAccount(Account account){
        if(account.username != "" && account.password.length()>=4){
            return accountDAO.insertAccount(account);
        }
            return null;
    }

    
    }


}

