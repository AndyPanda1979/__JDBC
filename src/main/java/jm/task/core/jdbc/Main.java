package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import javax.sql.rowset.spi.SyncResolver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws SQLException {
            UserServiceImpl userService = new UserServiceImpl();

            userService.createUsersTable();

            userService.saveUser("Юрий", "Гагарин", (byte) 35);
            userService.saveUser("Пётр", "Великий", (byte) 37);
            userService.saveUser("Валентина", "Терешкова", (byte) 30);
            userService.saveUser("Александра", "Потанина", (byte) 32);

            for (User item: userService.getAllUsers()) {
                System.out.println(item);
            }

            userService.cleanUsersTable();
            userService.dropUsersTable();

    }
}
