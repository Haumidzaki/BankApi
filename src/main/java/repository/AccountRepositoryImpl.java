package repository;

import model.Account;
import util.ConnectionFromBd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {
    private Connection connection;

    @Override
    public Account getById(long id) {
     /*   connection = ConnectionFromBd.getConnection();
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(String.format("SELECT * FROM accounts WHERE id = %d", id));

        while (set.next()){

        }
        return set.;*/
        return null;
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
