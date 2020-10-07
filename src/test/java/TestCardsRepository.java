import model.Account;
import model.Cards;
import model.Client;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.AccountRepositoryImpl;
import repository.CardRepositoryImpl;
import util.ConnectionFromBd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestCardsRepository {
    private CardRepositoryImpl cardRepository;
    private AccountRepositoryImpl accountRepository;

    @Before
    public void initBD() throws FileNotFoundException, SQLException {
        Connection connection = ConnectionFromBd.getConnection();

        RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
        RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));

        cardRepository = new CardRepositoryImpl();
        accountRepository = new AccountRepositoryImpl();
    }

    @After
    public void closeConnect(){
        ConnectionFromBd.closeConnection();
    }

    @Test
    public void getCardByNumber() {
        Client client = new Client(100_000l, "Vasay");
        Account account = new Account(100_002l, client, "1111111111", 1000d, "RUB");
        Cards oldCards = new Cards(100_004l, account, client, "1234 - 1234 - 1234 - 1234");
        Cards newCards = cardRepository.getCardByNumber("1234 - 1234 - 1234 - 1234");

        Assert.assertEquals(oldCards, newCards);

    }

    @Test
    public void getCardById() {
        Client client = new Client(100_000l, "Vasay");
        Account account = new Account(100_002l, client, "1111111111", 1000d, "RUB");
        Cards oldCards = new Cards(100_004l, account, client, "1234 - 1234 - 1234 - 1234");

        Cards newCards = cardRepository.getById(100_004l);

        Assert.assertEquals(oldCards, newCards);
    }

    @Test
    public void createCard() {
        Account account = accountRepository.getAll().get(0);
        Client client = account.getClient();
        Cards cards = new Cards(account, client, "8000-5555-4555-1337");
        boolean res = cardRepository.create(cards);

        Assert.assertTrue(res);
    }

    @Test
    public void updateCard() {
        long id = 100_004l;
        Cards oldCards = cardRepository.getById(id);
        Cards updateCards = new Cards(oldCards.getAccount(), oldCards.getClient(), "8000-5555-4555-1337");
        boolean res = cardRepository.update(updateCards, id);
        Cards newCards = cardRepository.getById(id);
        Assert.assertNotEquals(oldCards, newCards);
    }

    @Test
    public void deleteClient() {
        long id = 100_004l;

        boolean res = cardRepository.delete(id);
        List<Cards> list = cardRepository.getAll();

        for (Cards card : list){

            if(card.getId() == id){
                Assert.fail();
            }
        }

        Assert.assertTrue(res);
    }

    @Test
    public void getAllClient(){
        List<Cards> oldList = new ArrayList<>();

        Client client1 = new Client(100_000l, "Vasay");
        Account account1 = new Account(100_000l, client1, "1111111111", 1000d, "RUB");
        Cards oldCards1 = new Cards(100_002l, account1, client1, "1234 - 1234 - 1234 - 1234");

        Client client2 = new Client(100_001l, "Petya");
        Account account2 = new Account(100_001l, client2, "2222222222", 2000d, "RUB");
        Cards oldCards2 = new Cards(100_003l, account2, client2, "5555-4444-3333-2222");

        oldList.add(oldCards1);
        oldList.add(oldCards2);

        List<Cards> newList = cardRepository.getAll();

        Assert.assertEquals(oldList, newList);
    }
}
