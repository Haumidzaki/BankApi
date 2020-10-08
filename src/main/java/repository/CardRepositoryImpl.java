package repository;

import model.Account;
import model.Cards;
import model.Client;
import util.ConnectionFromBd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {
    private static final String SQL_GET_CARD_BY_NUMBER = "SELECT * FROM cards WHERE number LIKE ?";
    private static final String SQL_GET_CARD_BY_ID = "SELECT * FROM cards WHERE id = ?";
    private static final String SQL_CREATE_CARD = "INSERT INTO cards (account_id, number) VALUES (?, ?)";
    private static final String SQL_UPDATE_CARD = "UPDATE cards SET account_id = ?, number = ? WHERE id = ?";
    private static final String SQL_DELETE_CARD = "DELETE FROM cards WHERE id = ?";
    private static final String SQL_GET_ALL_CARD = "SELECT * FROM cards";

    private AccountRepository accountRepository;

    public CardRepositoryImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Cards getCardByNumber(String number) {
        Cards cards = null;
        try {
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_GET_CARD_BY_NUMBER);
            statement.setString(1, number);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Account account = accountRepository.getById(set.getInt("account_id"));
                Client client = account.getClient();
                cards = new Cards(set.getLong("id"), account, client, set.getString("number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Cards getById(long id) {
        Cards cards = null;
        Account account = null;
        try {
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_GET_CARD_BY_ID);
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                account = accountRepository.getById(set.getLong("account_id"));
                Client client = account.getClient();
                cards = new Cards(set.getLong("id"), account, client, set.getString("number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public boolean create(Cards cards) {
        boolean res = true;

        try {
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_CREATE_CARD);
            statement.setLong(1, cards.getAccount().getId());
            statement.setString(2, cards.getNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(Cards cards, long id) {
        boolean res = true;

        try {
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_UPDATE_CARD);
            statement.setLong(1, cards.getAccount().getId());
            statement.setString(2, cards.getNumber());
            statement.setLong(3, id);
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
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_DELETE_CARD);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Cards> getAll() {
        List<Cards> list = new ArrayList<>();
        try {
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_GET_ALL_CARD);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Account account = accountRepository.getById(set.getInt("account_id"));
                Client client = account.getClient();
                list.add(new Cards(set.getLong("id"), account, client, set.getString("number")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
