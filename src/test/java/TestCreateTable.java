import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import util.ConnectionFromBd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class TestCreateTable {

    @After
    public void closeConnect(){
        ConnectionFromBd.closeConnection();
    }

    @Test
    public void test1(){
        try {

            String createTableClients = "CREATE TABLE clients (id   BIGINT PRIMARY KEY, name VARCHAR NOT NULL)";
            String createTableAccounts = "CREATE TABLE accounts (id BIGINT PRIMARY KEY," +
                    "clients_id BIGINT NOT NULL," +
                    "number     VARCHAR NOT NULL," +
                    "sum        DOUBLE  NOT NULL," +
                    "currency   VARCHAR NOT NULL," +
                    "FOREIGN KEY (clients_id) REFERENCES clients (id) ON DELETE CASCADE)";
            String createTableCards = "CREATE TABLE cards (id BIGINT PRIMARY KEY," +
                    "account_id INTEGER NOT NULL," +
                    "number     VARCHAR NOT NULL," +
                    "FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE)";

            Connection connection = ConnectionFromBd.getConnection();

            try (PreparedStatement statement1 = connection.prepareStatement(createTableClients);
                 PreparedStatement statement2 = connection.prepareStatement(createTableAccounts);
                 PreparedStatement statement3 = connection.prepareStatement(createTableCards)) {
                statement1.execute();
                statement2.execute();
                statement3.execute();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        try {

            Connection connection = ConnectionFromBd.getConnection();

            RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
            RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));

            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM clients");
            String name = "";

            while (set.next()) {
                name = set.getString("name");
                if (name.equals("Petya"))
                    break;
            }
            Assert.assertEquals("Petya", name);

        }catch (SQLException | FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
