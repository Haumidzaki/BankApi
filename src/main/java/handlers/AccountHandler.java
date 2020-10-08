package handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Account;
import service.AccountService;

import java.io.IOException;
import java.io.OutputStream;

public class AccountHandler implements HttpHandler {
    private final AccountService accountService;

    public AccountHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    public void handle(HttpExchange t) throws IOException {
        String uri = t.getRequestURI().toString();
        String response;

        switch (t.getRequestMethod()) {
            case "GET":
                try {
                    response = getAccountBalance(uri);
                    t.sendResponseHeaders(200, response.length());
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    response = "Wrong id format";
                    t.sendResponseHeaders(400, response.length());
                }
                break;
            case "PATCH":
                response = updateAccountBalance(uri);
                if (response == null) {
                    response = "Not enough money to update balance";
                    t.sendResponseHeaders(400, response.length());
                } else {
                    t.sendResponseHeaders(200, response.length());
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

    public String getAccountBalance(String uri) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        long id = Long.parseLong(uri.split("/")[2]);
        Account account = accountService.getAccountInfo(id);
        return String.format("Balance of account with id = %d is %f", id, account.getSum());
    }

    public String updateAccountBalance(String uri) {
        String idWithParams = uri.split("/")[2];
        long id = Long.parseLong(idWithParams.split("\\?")[0]);
        double sum = Double.parseDouble(idWithParams.split("=")[1]);
        Account account = accountService.updateAccountSum(id, sum);
        if (account == null) return null;
        else return String.format("New balance of account with id = %d is %f", id, account.getSum());
    }
}

