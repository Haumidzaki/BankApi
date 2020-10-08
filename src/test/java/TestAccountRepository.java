import model.Account;
import model.Client;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.AccountRepository;
import repository.AccountRepositoryImpl;
import repository.ClientRepository;
import repository.ClientRepositoryImpl;
import util.ConnectionFromBd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TestAccountRepository {
    private AccountRepository accountrepository;
    private ClientRepository clientRepository;
    private AtomicLong newId;

    @Before
    public void initBD() {
        try {
            Connection connection = ConnectionFromBd.getConnection();

            RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
            RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));

            clientRepository = new ClientRepositoryImpl();
            accountrepository = new AccountRepositoryImpl(clientRepository);
            newId = new AtomicLong(100_006);

        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void closeConnect() {
        ConnectionFromBd.closeConnection();
    }

    @Test
    public void getAccountById() {
        Account oldClient = new Account(100_002l, new Client(100_000l, "Vasay"),
                "1111111111", 1000.0, "RUB");

        Account newAccount = accountrepository.getById(100_002);


        Assert.assertEquals(oldClient, newAccount);
    }

    @Test
    public void createAccount() {
        Client client = new Client(newId.getAndIncrement(), "Tor");
        clientRepository.create(client);

        Account newAccount = new Account(client,
                "55555", 1500d, "RUB");

        boolean res = accountrepository.create(newAccount);

        Assert.assertTrue(res);
    }

    @Test
    public void updateAccount() {
        long id = 100_002l;
        Account oldAccount = accountrepository.getById(id);

        boolean res = accountrepository.update(new Account(oldAccount.getClient(),
                oldAccount.getNumber(), oldAccount.getSum() + 3000, oldAccount.getCurrency()), id);

        Account newAccount = accountrepository.getById(id);

        Assert.assertNotEquals(oldAccount, newAccount);
        Assert.assertTrue(res);
    }

    @Test
    public void deleteAccount() {
        long id = 100_002l;

        boolean res = accountrepository.delete(id);
        List<Account> list = accountrepository.getAll();

        for (Account account : list) {

            if (account.getId() == id) {
                Assert.assertFalse(true);
            }
        }

        Assert.assertTrue(res);
    }

    @Test
    public void getAllAccount() {
        List<Account> oldList = new ArrayList<>();
        oldList.add(new Account(100_002l, new Client(100_000l, "Vasay"),
                "1111111111", 1000.0, "RUB"));
        oldList.add(new Account(100_003l, new Client(100001l, "Petya"),
                "2222222222", 2000.0, "RUB"));

        List<Account> newList = accountrepository.getAll();

        Assert.assertEquals(oldList, newList);
    }
}
