package util;

import java.sql.*;

public class ConnectionFromBd {
    private static final String DB_URL2 = "jdbc:h2:mem:default";
    private static Connection connection;

    public static Connection getConnection(){
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStatement() {
        Statement statement = null;
        try {
            statement = ConnectionFromBd.getConnection().createStatement();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return statement;
    }

    public static boolean isClose(){
        boolean res = true;
        try {
            res =  connection.isClosed();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }
}
