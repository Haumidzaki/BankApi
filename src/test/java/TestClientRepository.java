import model.Client;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.ClientRepositoryImpl;
import util.ConnectionFromBd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TestClientRepository {
    private ClientRepositoryImpl repository;

    @Before
    public void initBD() throws FileNotFoundException, SQLException {
        Connection connection = ConnectionFromBd.getConnection();

        RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
        RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));

        repository = new ClientRepositoryImpl();
    }

    @After
    public void closeConnect(){
        ConnectionFromBd.closeConnection();
    }

    @Test
    public void getClientById() {
        Client oldClient = new Client(100_001l,"Petya");

        Client newClient = repository.getById(100_001);

        Assert.assertEquals(oldClient, newClient);
    }

    @Test
    public void createClient() {
        Client oldClient = new Client("Tor");

        boolean res = repository.create(oldClient);

        Assert.assertTrue(res);
    }

    @Test
    public void updateClient() {
        long id = 100_001l;
        Client oldClient = repository.getById(id);

        boolean res = repository.update(new Client("Tor"), id);

        Client newClient = repository.getById(id);

        Assert.assertNotEquals(oldClient, newClient);
    }

    @Test
    public void deleteClient() {
        long id = 100_001l;

        boolean res = repository.delete(id);
        List<Client> list = repository.getAll();

        for (Client client : list){

            if(client.getId() == id){
                Assert.assertFalse(true);
            }
        }

        Assert.assertTrue(res);
    }

    @Test
    public void getAllClient(){
        List<Client> oldList = new ArrayList<>();
        oldList.add(new Client(100_000l, "Vasay"));
        oldList.add(new Client(100_001l, "Petya"));

        List<Client> newList = repository.getAll();

        Assert.assertEquals(oldList, newList);
    }

}
