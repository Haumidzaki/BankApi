package repository;

import model.Account;
import util.ConnectionFromBd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();

    @Override
    public Account getById(long id) {
        Account account = null;
        try {
          ResultSet set = ConnectionFromBd.getStatement()
                  .executeQuery(String.format("SELECT * FROM accounts WHERE id = %d", id));

          while (set.next()){
              account = new Account(set.getLong("id"),
                      clientRepository.getById(set.getLong("clients_id")),
                      set.getString("number"),
                      set.getDouble("sum"),
                      set.getString("currency"));
          }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public boolean create(Account account) {
        return false;
    }

    @Override
    public boolean update(Account account, long id) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<Account> getAll() {
        return null;
    }
}
