package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            if (!connection.isClosed()) {
                System.out.println("Connected OK");
            }
            //connection.close();
            if (connection.isClosed()) {
                System.out.println("Connected disconnect");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Нет подключения к драйверу");
        }
        return connection;
    }
}
    // реализуйте настройку соеденения с БД

