package repository;

import model.Cards;
import model.Client;

import java.util.List;

public interface StandartRepo<T> {

    T getById(long id);

    List<T> getAll();

    boolean create(T t);

    boolean update(T t, long id);

    boolean delete(long id);
}
