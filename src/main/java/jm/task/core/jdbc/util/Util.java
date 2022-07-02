package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private static Connection connection;
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/test1", "root", "admin123");
            }
            return connection;
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
            e.printStackTrace();
        }

        return null;
    }

}
