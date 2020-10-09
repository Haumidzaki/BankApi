import org.h2.tools.RunScript;
import util.ConnectionFromBd;
import util.HttpServerController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Test");
            Connection connection = ConnectionFromBd.getConnection();
            RunScript.execute(connection, new InputStreamReader(Main.class.getResourceAsStream("bd/initBD.sql")));
            RunScript.execute(connection, new InputStreamReader(Main.class.getResourceAsStream("bd/populateBD.sql")));
            HttpServerController.startServer();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
