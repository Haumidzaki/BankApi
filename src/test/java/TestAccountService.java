import model.Account;
import model.Cards;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.AccountService;
import service.AccountServiceImpl;
import util.ConnectionFromBd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestAccountService {
    private AccountService accountService;

    @Before
    public void initBD() throws FileNotFoundException, SQLException {
        Connection connection = ConnectionFromBd.getConnection();

        RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
        RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));

        accountService = new AccountServiceImpl();
    }

    @After
    public void closeConnect() {
        ConnectionFromBd.closeConnection();
    }

    @Test
    public void createNewCard() {
        long accountId = 100_002l;
        List<Cards> oldListCard = accountService.getListOfCards(accountId);

        accountService.createNewCard(accountId);

        List<Cards> newListCard = accountService.getListOfCards(accountId);

        Assert.assertNotEquals(oldListCard, newListCard);
    }

    @Test
    public void updateAccountSum() {
        long accountId = 100_002l;
        double addSum = 4000d;

        Account oldAccount = accountService.getAccountInfo(accountId);
        double oldSum = oldAccount.getSum();

        accountService.updateAccountSum(accountId, addSum);

        Account newAccount = accountService.getAccountInfo(accountId);
        double newSum = newAccount.getSum();

        Assert.assertNotEquals(oldSum, newSum);
    }

    @Test
    public void getAccountInfo() {
        long accountId1 = 100_002l;
        long accountId2 = 100_003l;
        Account oldAccount = accountService.getAccountInfo(accountId1);

        Account newAccount = accountService.getAccountInfo(accountId1);

        Account wtfAccount = accountService.getAccountInfo(accountId2);

        Assert.assertEquals(oldAccount, newAccount);

        Assert.assertNotEquals(newAccount, wtfAccount);
    }

    @Test
    public void getListOfCards() {
        long accountId = 100_002l;

        List<Cards> oldList = accountService.getListOfCards(accountId);

        int sizeOldList = oldList.size();

        accountService.createNewCard(accountId);

        List<Cards> newList = accountService.getListOfCards(accountId);

        int sizeNewList = newList.size();

        Assert.assertNotEquals(sizeOldList, sizeNewList);
    }
}
