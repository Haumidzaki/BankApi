import model.Client;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import util.ConnectionFromBd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestClientRepository {
    private ClientRepository clientRepository;

    @Before
    public void initBD() throws FileNotFoundException, SQLException {
        Connection connection = ConnectionFromBd.getConnection();

        RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
        RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));

        clientRepository = new ClientRepositoryImpl();
    }

    @After
    public void closeConnect() {
        ConnectionFromBd.closeConnection();
    }

    @Test
    public void getClientById() {
        Client oldClient = new Client(100_001l, "Petya");

        Client newClient = clientRepository.getById(100_001);

        Assert.assertEquals(oldClient, newClient);
    }

    @Test
    public void createClient() {
        Client oldClient = new Client("Tor");

        boolean res = clientRepository.create(oldClient);

        Assert.assertTrue(res);
    }

    @Test
    public void updateClient() {
        long id = 100_001l;
        Client oldClient = clientRepository.getById(id);

        boolean res = clientRepository.update(new Client("Tor"), id);

        Client newClient = clientRepository.getById(id);

        Assert.assertNotEquals(oldClient, newClient);
    }

    @Test
    public void deleteClient() {
        long id = 100_001l;

        boolean res = clientRepository.delete(id);
        List<Client> list = clientRepository.getAll();

        for (Client client : list) {

            if (client.getId() == id) {
                Assert.assertFalse(true);
            }
        }

        Assert.assertTrue(res);
    }

    @Test
    public void getAllClient() {
        List<Client> oldList = new ArrayList<>();
        oldList.add(new Client(100_000l, "Vasay"));
        oldList.add(new Client(100_001l, "Petya"));

        List<Client> newList = clientRepository.getAll();

        Assert.assertEquals(oldList, newList);
    }
}
