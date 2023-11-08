package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL_DB = "jdbc:mysql://localhost:3306/predb";
    private static final String USER = "test1";
    private static final String PSW = "test1";
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);

            connection = DriverManager.getConnection(URL_DB, USER, PSW);
            if (!connection.isClosed()) {
                return connection;
            }
        } catch (SQLException e) {
            System.err.println("Проблемы с драйвером подключения к БД");
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection == null || connection.isClosed()) {
                return;
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("При закрытии соединения возникла ошибка!");
        }
    }
}
