package handlers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Cards;
import service.AccountService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class CardHandler implements HttpHandler {
    private final AccountService accountService;

    public CardHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    public void handle(HttpExchange t) throws IOException {
        String uri = t.getRequestURI().toString();
        String response;

        switch (t.getRequestMethod()) {
            case "GET":
                try {
                    response = getListOfCards(uri);
                    t.sendResponseHeaders(200, response.length());
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    response = "Wrong id format";
                    t.sendResponseHeaders(400, response.length());
                } catch (JsonProcessingException e) {
                    response = "Internal server error";
                    t.sendResponseHeaders(500, response.length());
                }
                break;
            case "POST":
                try {
                    response = createCardForAccount(uri);
                    t.sendResponseHeaders(200, response.length());
                } catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    response = "Wrong id format";
                    t.sendResponseHeaders(400, response.length());
                } catch (JsonProcessingException e) {
                    response = "Internal server error";
                    t.sendResponseHeaders(500, response.length());
                }
                break;
            default:
                response = "Wrong request method";
                t.sendResponseHeaders(400, response.length());
        }
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String getListOfCards(String uri) throws NumberFormatException, ArrayIndexOutOfBoundsException, JsonProcessingException {
        long id = Long.parseLong(uri.split("/")[2]);
        List<Cards> cards = accountService.getListOfCards(id);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(cards);
    }

    public String createCardForAccount(String uri) throws NumberFormatException, ArrayIndexOutOfBoundsException, JsonProcessingException{
        long id = Long.parseLong(uri.split("/")[2]);
        Cards cards = accountService.createNewCard(id);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(cards);
    }
}
