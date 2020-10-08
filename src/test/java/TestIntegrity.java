import com.fasterxml.jackson.databind.ObjectMapper;
import model.Account;
import model.Cards;
import model.Client;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.ConnectionFromBd;
import util.HttpServerController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestIntegrity {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private Client client = new Client(100_000L, "Vasay");
    private Account account = new Account(100_002L, client, "1111111111", 1000d, "RUB");
    private Cards card = new Cards(100_004L, account, client, "1234-1234-1234-1234");

    @Before
    public void startServer() throws FileNotFoundException, SQLException {
        Connection connection = ConnectionFromBd.getConnection();
        RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
        RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));
        HttpServerController.startServer();
    }

    @After
    public void shutDownServer() {
        HttpServerController.stopServer();
    }

    @Test
    public void checkBalance() {
        String url = String.format("http://localhost:8000/account/%d", account.getId());
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseCode = response.getStatusLine().toString().split("\\s")[1];
            String expectedResponseCode = "200";
            Assert.assertEquals(expectedResponseCode, responseCode);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity);
            String expectedResult = String.format("Balance of account with id = %d is %.2f", account.getId(), account.getSum());
            Assert.assertEquals(expectedResult, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateBalance() {
        String url = String.format("http://localhost:8000/account/%d?sum=%d", account.getId(), 1000);
        HttpPatch request = new HttpPatch(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseCode = response.getStatusLine().toString().split("\\s")[1];
            String expectedResponseCode = "200";
            Assert.assertEquals(expectedResponseCode, responseCode);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity);
            String expectedResult = String.format("New balance of account with id = %d is %.2f", account.getId(), account.getSum() + 1000);
            Assert.assertEquals(expectedResult, result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getListOfCards() {
        String url = String.format("http://localhost:8000/card/%d", account.getId());
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseCode = response.getStatusLine().toString().split("\\s")[1];
            String expectedResponseCode = "200";
            Assert.assertEquals(expectedResponseCode, responseCode);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Cards> list = new ArrayList<>();
            list.add(card);
            String expectedResult = objectMapper.writeValueAsString(list);
            Assert.assertEquals(expectedResult, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createCard() {
        String url = String.format("http://localhost:8000/card/%d", account.getId());
        HttpPost request = new HttpPost(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String responseCode = response.getStatusLine().toString().split("\\s")[1];
            String expectedResponseCode = "200";
            Assert.assertEquals(expectedResponseCode, responseCode);
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity);
            Assert.assertNotNull(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
