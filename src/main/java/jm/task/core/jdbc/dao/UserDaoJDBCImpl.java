package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public void createUsersTable() {
        String createUsersQuery = "CREATE TABLE IF NOT EXISTS userTable(" +
                "id bigint auto_increment primary key, " +
                "name varchar(255) null, " +
                "lastname varchar(255) null, " +
                "age tinyint null)";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            statement.execute(createUsersQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropUsersQuery = "drop table if exists usertable;";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            statement.execute(dropUsersQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserInTableQuery = "insert usertable(name, lastname, age) " +
                "values('" + name + "','" + lastName + "'," + age + ");";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            statement.execute(saveUserInTableQuery);
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUserQuery = "delete from usertable where id=" + id + ";";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            statement.execute(removeUserQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String resultSetQuery = "select * from usertable;";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(resultSetQuery);
            while (resultSet.next()) {
                User tmpUser = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                tmpUser.setId(resultSet.getLong(1));
                usersList.add(tmpUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String cleanTableQuery = "TRUNCATE TABLE usertable;";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();) {
            statement.execute(cleanTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
