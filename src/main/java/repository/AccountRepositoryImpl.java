package repository;

import model.Account;
import util.ConnectionFromBd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {
    private static final String SQL_GET_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE id = ?";
    private static final String SQL_CREATE_ACCOUNT = "INSERT INTO accounts (clients_id, number, sum, currency) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_ACCOUNT = "UPDATE accounts SET clients_id = ?, number = ?, sum = ?, currency = ? WHERE id = ?";
    private static final String SQL_DELETE_ACCOUNT = "DELETE FROM accounts WHERE id = ?";
    private static final String SQL_GET_ALL_ACCOUNT = "SELECT * FROM accounts";

    private ClientRepository clientRepository;

    public AccountRepositoryImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Account getById(long id) {
        Account account = null;
        try {
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_GET_ACCOUNT_BY_ID);
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                account = new Account(set.getLong("id"),
                        clientRepository.getById(set.getLong("clients_id")),
                        set.getString("number"),
                        set.getDouble("sum"),
                        set.getString("currency"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public boolean create(Account account) {
        boolean res = true;
        try {
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_CREATE_ACCOUNT);
            statement.setLong(1, account.getClient().getId());
            statement.setString(2, account.getNumber());
            statement.setDouble(3, account.getSum());
            statement.setString(4, account.getCurrency());
            statement.executeUpdate();
        } catch (SQLException e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(Account account, long id) {
        boolean res = true;

        try {
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_UPDATE_ACCOUNT);
            statement.setLong(1, account.getClient().getId());
            statement.setString(2, account.getNumber());
            statement.setDouble(3, account.getSum());
            statement.setString(4, account.getCurrency());
            statement.setLong(5, id);

            statement.executeUpdate();
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
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_DELETE_ACCOUNT);
            statement.setLong(1, id);
            statement.executeUpdate();
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
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_GET_ALL_ACCOUNT);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
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
