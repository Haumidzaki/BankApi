import model.Cards;
import model.Client;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.CardRepositoryImpl;
import repository.ClientRepositoryImpl;
import util.ConnectionFromBd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class TestCardsRepository {
    private CardRepositoryImpl repository;

    @Before
    public void initBD() throws FileNotFoundException, SQLException {
        Connection connection = ConnectionFromBd.getConnection();

        RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
        RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));

        repository = new CardRepositoryImpl();
    }

    @After
    public void closeConnect(){
        ConnectionFromBd.closeConnection();
    }

    @Test
    public void getCardById() {

    }
}
