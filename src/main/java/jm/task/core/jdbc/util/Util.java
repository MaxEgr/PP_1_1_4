package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.System.setProperty;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://localhost:3306/users";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "user";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";

    public  static Connection connection = connection();
    private static Connection connection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(HOST, LOGIN, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error connect with DB");
            throw new RuntimeException(e);
        }
        return connection;
    }

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
    try{
        Configuration configuration = new Configuration()
        .setProperty("hibernate.connection.url", HOST)
        .setProperty("dialect", DIALECT)
        .setProperty("hibernate.connection.username", LOGIN)
        .setProperty("hibernate.connection.password", PASSWORD)
        .setProperty("hibernate.connection.driver_class", DRIVER)
        .addAnnotatedClass(User.class)
        .setProperty("hibernate.c3p0.min_size", "5")
        .setProperty("hibernate.c3p0.max_size", "200")
        .setProperty("hibernate.c3p0.max_statements", "200");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    } catch (HibernateException e) {
        e.printStackTrace();
    }
        return sessionFactory;
    }
}