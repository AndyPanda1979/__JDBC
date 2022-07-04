package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try (Connection currentConnection = Util.getConnection()) {
            if (currentConnection != null) {
                try {
                    currentConnection.setAutoCommit(false);
                    String rawSQL = """
                        CREATE TABLE IF NOT EXISTS `test1`.`users` (
                        `id` BIGINT(64) NOT NULL AUTO_INCREMENT,
                        `name` VARCHAR(45) NOT NULL,
                        `lastName` VARCHAR(45) NOT NULL,
                        `age` INT NOT NULL,
                        PRIMARY KEY (`id`));""";
                    currentConnection.createStatement().executeUpdate(rawSQL);
                    currentConnection.commit();
                } catch (SQLException e) {
                    System.out.println("Не удалось создать таблицу");
                    e.printStackTrace();
                    currentConnection.rollback();
                    currentConnection.close();
                }
            } else {
                System.out.println("Не удалось установить соединение с БД");
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection currentConnection = Util.getConnection()) {
            if (currentConnection != null) {
                try {
                    currentConnection.setAutoCommit(false);
                    String rawSQL = "DROP TABLE IF EXISTS `test1`.`users`;";
                    currentConnection.createStatement().executeUpdate(rawSQL);
                    currentConnection.commit();
                } catch (SQLException e) {
                    System.out.println("Не удалось удалить таблицу");
                    currentConnection.rollback();
                    currentConnection.close();
                }
            } else {
                System.out.println("Не удалось установить соединение с БД");
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Connection currentConnection = Util.getConnection()) {
            if (currentConnection != null) {
                try (PreparedStatement preparedStatement = currentConnection.prepareStatement("INSERT INTO `test1`.`users` (`name`, `lastName`, `age`) VALUES (?, ?, ?)")) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setByte(3, age);
                    currentConnection.setAutoCommit(false);
                    preparedStatement.executeUpdate();
                    currentConnection.commit();
                    System.out.println("User с именем - " + name + " " + lastName + " добавлен в БД");
                } catch (SQLException e) {
                    System.out.println("Ошибка вставки записи, User не был добавлен");
                    currentConnection.rollback();
                    currentConnection.close();
                }
            } else {
                System.out.println("Не удалось установить соединение с БД");
            }
        }
    }


    public void removeUserById(long id) throws SQLException {
        try (Connection currentConnection = Util.getConnection()) {
            if (currentConnection != null) {
                try (PreparedStatement preparedStatement = currentConnection.prepareStatement("DELETE FROM `users` WHERE `id`=(?)")) {
                    preparedStatement.setLong(1, id);
                    currentConnection.setAutoCommit(false);
                    preparedStatement.executeUpdate();
                    currentConnection.commit();
                } catch (SQLException e) {
                    System.out.println("Ошибка удаления записи");
                    currentConnection.rollback();
                    currentConnection.close();
                }
            } else {
                System.out.println("Не удалось установить соединение с БД");
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        try (Connection currentConnection = Util.getConnection()) {
            if (currentConnection != null) {
                try (Statement statement = currentConnection.createStatement()) {
                    String rawSQL = "SELECT * FROM test1.users;";
                    currentConnection.setAutoCommit(false);
                    ResultSet result = statement.executeQuery(rawSQL);
                    currentConnection.commit();
                    List<User> users = new ArrayList<>();
                    while (result.next()) {
                        User currentUser;
                        users.add(currentUser = new User(result.getString("name"), result.getString("lastName"), result.getByte("age")));
                        currentUser.setId(result.getLong("id"));
                    }
                    return users;
                } catch (SQLException e) {
                    System.out.println("Не удалось получить Users");
                }
            } else {
                System.out.println("Не удалось установить соединение с БД");
            }
        }
        return new ArrayList<>();
    }

    public void cleanUsersTable() throws SQLException {
        try (Connection currentConnection = Util.getConnection()) {
            if (currentConnection != null) {
                try {
                    currentConnection.setAutoCommit(false);
                    String rawSQL = "TRUNCATE TABLE `users`;";
                    currentConnection.createStatement().executeUpdate(rawSQL);
                    currentConnection.commit();
                } catch (SQLException e) {
                    System.out.println("Не удалось очистить таблицу");
                    currentConnection.rollback();
                    currentConnection.close();
                }
            } else {
                System.out.println("Не удалось установить соединение с БД");
            }
        }
    }
}
