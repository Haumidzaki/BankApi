package util;

import java.sql.*;

public class ConnectionFromBd {
    private static final String DB_URL2 = "jdbc:h2:mem:default";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed())
                return connection;
            else
                connection = DriverManager.getConnection(DB_URL2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if(!connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isClose() {
        boolean res = false;
        try {
            res = connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
