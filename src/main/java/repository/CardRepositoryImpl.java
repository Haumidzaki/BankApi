package repository;

import model.Cards;

import java.sql.Connection;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {
    private Connection connection;

    @Override
    public Cards getCardByNumber(String number) {
        return null;
    }

    @Override
    public Cards getById(long id) {
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
