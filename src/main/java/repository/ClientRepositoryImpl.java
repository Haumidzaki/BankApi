package repository;

import model.Client;
import util.ConnectionFromBd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClientRepositoryImpl implements ClientRepository {
    private static final String SQL_GET_CLIENT_BY_ID = "SELECT * FROM clients WHERE id = ?";
    private static final String SQL_CREATE_CLIENT = "INSERT INTO clients (name) VALUES (?)";
    private static final String SQL_UPDATE_CLIENT = "UPDATE clients SET name = ? WHERE id = ?";
    private static final String SQL_DELETE_CLIENT = "DELETE FROM clients WHERE id = ?";
    private static final String SQL_GET_ALL_CLIENT = "SELECT * FROM clients";

    @Override
    public Client getById(long id) {
        Client client = null;

        try {
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_GET_CLIENT_BY_ID);
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
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
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_CREATE_CLIENT);
            statement.setString(1, client.getName());
            statement.executeUpdate();
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
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_UPDATE_CLIENT);
            statement.setString(1, client.getName());
            statement.setLong(2, id);
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
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_DELETE_CLIENT);
            statement.setLong(1, id);
            statement.executeUpdate();
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
            PreparedStatement statement = ConnectionFromBd.getConnection().prepareStatement(SQL_GET_ALL_CLIENT);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                list.add(new Client(set.getLong("id"), set.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
