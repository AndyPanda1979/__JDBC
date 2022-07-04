package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

//    private static Connection connection;
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/test1", "root", "admin123");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
