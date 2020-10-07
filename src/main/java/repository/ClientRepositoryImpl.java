package repository;

import model.Client;
import util.ConnectionFromBd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClientRepositoryImpl implements ClientRepository {


    @Override
    public Client getById(long id) {
        Client client = null;

        try {
            ResultSet set = ConnectionFromBd.getStatement()
                    .executeQuery(String.format("SELECT * FROM clients WHERE id = %d", id));
            while (set.next()) {
                client = new Client(set.getLong("id"), set.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }

    @Override
    public boolean create(Client client) {
        boolean res = true;

        try {
            ConnectionFromBd.getStatement().executeUpdate(String
                    .format("INSERT INTO clients (name) VALUES ('%s')", client.getName()));
        } catch (SQLException e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean update(Client client, long id) {
        boolean res = true;

        try {
            ConnectionFromBd.getStatement().executeUpdate(String
                    .format("UPDATE clients SET name = '%s' WHERE id = '%d'", client.getName(), id));
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
            ConnectionFromBd.getStatement().executeUpdate(String.format("DELETE FROM clients WHERE id = %d", id));
        } catch (SQLException e) {
            res = false;
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Client> getAll() {
        List<Client> list = new ArrayList<>();
        try {
            ResultSet set = ConnectionFromBd.getStatement().executeQuery("SELECT * FROM clients");

            while (set.next()){
                list.add(new Client(set.getLong("id"), set.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
