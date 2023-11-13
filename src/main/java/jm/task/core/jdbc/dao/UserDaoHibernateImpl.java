package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String createUsersQuery = "CREATE TABLE IF NOT EXISTS userTable(" +
                "id bigint auto_increment primary key, " +
                "name varchar(255) null, " +
                "lastname varchar(255) null, " +
                "age tinyint null)";

        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(createUsersQuery).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String dropUsersQuery = "DROP TABLE IF EXISTS usertable;";
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(dropUsersQuery).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            User userToDelete = session.find(User.class, id);
            session.remove(userToDelete);
            session.flush();
            session.clear();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> resultListUsers = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            resultListUsers = session.createQuery("FROM " + User.class.getSimpleName()).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultListUsers;
    }

    @Override
    public void cleanUsersTable() {
        String cleanTableQuery = "TRUNCATE TABLE usertable;";
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(cleanTableQuery).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
