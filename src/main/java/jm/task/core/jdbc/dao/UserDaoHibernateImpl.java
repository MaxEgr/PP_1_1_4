package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction = null;
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {

    try (Session session = Util.sessionFactory.openSession()){
        transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users.user" +
                "(id mediumint not null auto_increment," +
                " name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age tinyint, " +
                "PRIMARY KEY (id))";
        session.createSQLQuery(sql).executeUpdate();
        session.getTransaction().commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = Util.sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "Drop table if exists users.user";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM users.user where id").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = Util.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("SELECT id, name, lastName, age from users.user").getResultList();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM users.user").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    @Override
    public void close() {

    }
}

