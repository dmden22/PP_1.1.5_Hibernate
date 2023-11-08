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
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS userTable(" +
                    "id bigint auto_increment primary key, " +
                    "name varchar(255) null, " +
                    "lastname varchar(255) null, " +
                    "age tinyint null);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Util.closeConnection(connection);
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("drop table if exists usertable;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Util.closeConnection(connection);
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("insert usertable(name, lastname, age) " +
                    "values('" + name + "','" + lastName + "'," + age + ");");
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Util.closeConnection(connection);
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("delete from usertable where id=" + id + ";");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Util.closeConnection(connection);
    }

    public List<User> getAllUsers() {
        List<User> resultList = new ArrayList<>();
        Connection connection = Util.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from usertable;");
            while (resultSet.next()) {
                User tmpUser = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                tmpUser.setId(resultSet.getLong(1));
                resultList.add(tmpUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Util.closeConnection(connection);
        return resultList;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("TRUNCATE TABLE usertable;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Util.closeConnection(connection);
    }
}
