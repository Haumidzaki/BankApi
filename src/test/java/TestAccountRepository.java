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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class TestAccountRepository {
    private AccountRepositoryImpl repository;

    @Before
    public void initBD() throws FileNotFoundException, SQLException {
        Connection connection = ConnectionFromBd.getConnection();

        RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
        RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));

        repository = new AccountRepositoryImpl();
    }

    @After
    public void closeConnect(){
        ConnectionFromBd.closeConnection();
    }

    @Test
    public void getClientById() {
        Account oldClient = new Account(100_002l, new Client(100_000l,"Vasay"), "1111111111", 1000.0, "RUB");

    }
}
