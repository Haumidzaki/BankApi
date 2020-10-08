package util;

import com.sun.net.httpserver.HttpServer;
import handlers.AccountHandler;
import handlers.CardHandler;
import handlers.TestHandler;
import service.AccountServiceImpl;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerController {
    private static HttpServer server;

    public static void startServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/test", new TestHandler());
            server.createContext("/account", new AccountHandler(new AccountServiceImpl()));
            server.createContext("/card", new CardHandler(new AccountServiceImpl()));
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopServer() {
        server.stop(0);
    }
}
