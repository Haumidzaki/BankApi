package util;

import com.sun.net.httpserver.HttpServer;
import handlers.AccountHandler;
import handlers.CardHandler;
import handlers.TestHandler;
import service.AccountServiceImplStub;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerStarter {

    public static void startServer(){
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/test", new TestHandler());
            server.createContext("/account", new AccountHandler(new AccountServiceImplStub()));
            server.createContext("/card", new CardHandler(new AccountServiceImplStub()));
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
