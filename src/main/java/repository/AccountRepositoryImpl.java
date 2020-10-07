package repository;

import model.Account;
import model.Client;
import util.ConnectionFromBd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        boolean res = true;
        try {
            ConnectionFromBd.getStatement().executeUpdate(String
                    .format("INSERT INTO accounts (clients_id, number, sum, currency) VALUES (%d, '%s', '%s', '%s')",
                            account.getClient().getId(),
                            account.getNumber(),
                            account.getSum(),
                            account.getCurrency()));
        }catch (SQLException e){
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(Account account, long id) {
        boolean res = true;

        try {
            ConnectionFromBd.getStatement().executeUpdate(String
                    .format("UPDATE accounts SET clients_id = '%d', number = '%s', sum = '%s', currency = '%s' WHERE id = '%d'",
                            account.getClient().getId(),
                            account.getNumber(),
                            account.getSum(),
                            account.getCurrency(),
                            id));
        } catch (SQLException e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean delete(long id) {
        boolean res = true;

        try {
            ConnectionFromBd.getStatement().executeUpdate(String.format("DELETE FROM accounts WHERE id = %d", id));
        } catch (SQLException e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Account> getAll() {
        List<Account> list = new ArrayList<>();
        try {
            ResultSet set = ConnectionFromBd.getStatement().executeQuery("SELECT * FROM accounts");

            while (set.next()){
                list.add(new Account(set.getLong("id"),
                        clientRepository.getById(set.getLong("clients_id")),
                        set.getString("number"),
                        set.getDouble("sum"),
                        set.getString("currency")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
