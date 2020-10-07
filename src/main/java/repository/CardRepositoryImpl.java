package repository;

import model.Account;
import model.Cards;
import model.Client;
import util.ConnectionFromBd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    private AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();

    @Override
    public Cards getCardByNumber(String number) {
        Cards cards = null;
        try {
            ResultSet set = ConnectionFromBd.getStatement().executeQuery(String.format("SELECT * FROM cards WHERE number LIKE '%s'", number));
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
            ResultSet set = ConnectionFromBd.getStatement().executeQuery(String.format("SELECT * FROM cards WHERE id = %d", id));
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
            ConnectionFromBd.getStatement().executeUpdate(String
                    .format("INSERT INTO cards (account_id, number) VALUES (%s, '%s')", cards.getAccount().getId(), cards.getNumber()));
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
            ConnectionFromBd.getStatement().executeUpdate(String
                    .format("UPDATE cards SET account_id = '%d', number = %s WHERE id = '%d'",
                            cards.getAccount().getId(), cards.getNumber(), id));
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
            ConnectionFromBd.getStatement().executeUpdate(String.format("DELETE FROM cards WHERE id = %d", id));
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
            ResultSet set = ConnectionFromBd.getStatement().executeQuery("SELECT * FROM cards");
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
