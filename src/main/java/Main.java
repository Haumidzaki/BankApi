import org.h2.tools.RunScript;
import util.ConnectionFromBd;
import util.HttpServerController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Test");
            Connection connection = ConnectionFromBd.getConnection();
            RunScript.execute(connection, new FileReader("src/main/resources/bd/initBD.sql"));
            RunScript.execute(connection, new FileReader("src/main/resources/bd/populateBD.sql"));
            HttpServerController.startServer();
        } catch (FileNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}
