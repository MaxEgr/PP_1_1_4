package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction = null;
    private  final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {

    try (Session session = sessionFactory.openSession()){
        transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users.user" +
                "(id mediumint not null auto_increment," +
                " name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age tinyint, " +
                "PRIMARY KEY (id))";
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            String sql = "Drop table if exists users.user";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User where id = " + id).executeUpdate();
            session.getTransaction().commit();

        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> list = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            transaction.commit();
        }
    }
    @Override
    public void close() {

    }
}

