import model.Account;
import model.Client;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.AccountRepositoryImpl;
import repository.ClientRepositoryImpl;
import util.ConnectionFromBd;
import util.GetNewId;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestAccountRepository {
    private AccountRepositoryImpl repository;
    private ClientRepositoryImpl clientRepository;

    @Before
    public void initBD() throws FileNotFoundException, SQLException {
        Connection connection = ConnectionFromBd.getConnection();

        RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
        RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));

        repository = new AccountRepositoryImpl();
        clientRepository = new ClientRepositoryImpl();
    }

    @After
    public void closeConnect(){
        ConnectionFromBd.closeConnection();
    }

    @Test
    public void getAccountById() {
        Account oldClient = new Account(100_002l, new Client(100_000l, "Vasay"),
                "1111111111", 1000.0, "RUB");

        Account newAccount = repository.getById(100_002);


        Assert.assertEquals(oldClient, newAccount);
    }

    @Test
    public void createAccount() {
        Client client = new Client(GetNewId.getNewId(), "Tor");
        clientRepository.create(client);

        Account newAccount = new Account(client,
                "55555", 1500d, "RUB");

        boolean res = repository.create(newAccount);

        Assert.assertTrue(res);
    }

    @Test
    public void updateAccount() {
        long id = 100_002l;
        Account oldAccount = repository.getById(id);

        boolean res = repository.update(new Account(oldAccount.getClient(),
                oldAccount.getNumber(), oldAccount.getSum() + 3000, oldAccount.getCurrency()), id);

        Account newAccount = repository.getById(id);

        Assert.assertNotEquals(oldAccount, newAccount);
    }

    @Test
    public void deleteAccount() {
        long id = 100_002l;

        boolean res = repository.delete(id);
        List<Account> list = repository.getAll();

        for (Account account : list){

            if(account.getId() == id){
                Assert.assertFalse(true);
            }
        }

        Assert.assertTrue(res);
    }

    @Test
    public void getAllAccount(){
        List<Account> oldList = new ArrayList<>();
        oldList.add(new Account(100_002l, new Client(100_000l, "Vasay"),
                "1111111111", 1000.0, "RUB"));
        oldList.add(new Account(100_003l, new Client(100001l, "Petya"),
                "2222222222", 2000.0, "RUB"));

        List<Account> newList = repository.getAll();

        Assert.assertEquals(oldList, newList);
    }
}
