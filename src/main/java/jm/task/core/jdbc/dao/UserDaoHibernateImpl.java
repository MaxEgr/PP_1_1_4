package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
    try (Session session = Util.sessionFactory.openSession()){
        session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users.user" +
                "(id mediumint not null auto_increment," +
                " name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age tinyint, " +
                "PRIMARY KEY (id))";
        session.createSQLQuery(sql).executeUpdate();
        session.getTransaction().commit();
    }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.sessionFactory.openSession()){
            session.beginTransaction();
            String sql = "Drop table if exists users.user";
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.sessionFactory.openSession()){
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM users.user where id").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> list = session.createQuery("SELECT id, name, lastName, age from users.user").getResultList();
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM users.user").executeUpdate();
            session.getTransaction().commit();
        }
    }
    @Override
    public void close() {

    }
}

