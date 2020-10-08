package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        System.out.println(uri);
        long id = Long.parseLong(uri.split("/")[2]);
        Account account = accountService.getAccountInfo(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(account);

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

