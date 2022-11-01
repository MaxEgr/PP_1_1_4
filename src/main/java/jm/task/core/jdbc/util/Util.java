package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://localhost:3306/users";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "user";
    private static final String DIALECT = "org.hibernate.dialect.MySQL8Dialect";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(HOST, LOGIN, PASSWORD);
            connection.setAutoCommit(false);
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static SessionFactory sessionFactory = new Configuration()
            .addProperties(getPropertiesSessionFactory())
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

    private static Properties getPropertiesSessionFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", HOST);
        properties.setProperty("dialect", DIALECT);
        properties.setProperty("hibernate.connection.username", LOGIN);
        properties.setProperty("hibernate.connection.password", PASSWORD);
        properties.setProperty("hibernate.connection.driver_class", DRIVER);
        return properties;
    }
}