package repository;

import model.Account;
import model.Cards;
import util.ConnectionFromBd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    @Override
    public Cards getCardByNumber(String number) {
        return null;
    }

    @Override
    public Cards getById(long id) {
        Cards cards = null;
        try {
            ResultSet set = ConnectionFromBd.getStatement().executeQuery(String.format("SELECT * FROM cards WHERE id = %d", id));


        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Cards cards) {
        return false;
    }

    @Override
    public boolean update(Cards cards, long id) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<Cards> getAll() {
        return null;
    }
}
