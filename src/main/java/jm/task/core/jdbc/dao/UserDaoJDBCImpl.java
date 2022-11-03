package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws NullPointerException {
        try {
            Util.connection.setAutoCommit(false);
            String string = "CREATE TABLE IF NOT EXISTS users.user (id mediumint not null auto_increment, name VARCHAR(50), lastname VARCHAR(50), age tinyint, PRIMARY KEY (id))\n";
            PreparedStatement preparedStatement = Util.connection.prepareStatement(string);
            preparedStatement.executeUpdate();
            System.out.println("Таблица создана");
            Util.connection.commit();
        } catch (SQLException e) {
            try {
                Util.connection.rollback();
                Util.connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Ошибка создания table");
        }
        finally {
            try {
                Util.connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error setAutoCommit(true)");
            }
        }
    }
    public void dropUsersTable() {
        try {
            Util.connection.setAutoCommit(false);
            PreparedStatement preparedStatement = Util.connection.prepareStatement("Drop table if exists users.user");
            preparedStatement.executeUpdate();
            System.out.println("Таблица удалена");
            Util.connection.commit();
        } catch (SQLException e) {
            try {
                Util.connection.rollback();
                Util.connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Ошибка удаления таблицы");
        }
        finally {
            try {
                Util.connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error setAutoCommit(true)");
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Util.connection.setAutoCommit(false);
            PreparedStatement preparedStatement = Util.connection.prepareStatement("INSERT INTO users.user(name, lastname, age) VALUES(?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            Util.connection.commit();
        } catch (SQLException e) {
            try {
                Util.connection.rollback();
                Util.connection.close();
            } catch (SQLException ex) {
                System.out.println(" ");
            }
            System.out.println(" ");
        }
        finally {
            try {
                Util.connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(" ");;
            }
        }
    }

    public void removeUserById(long id) {
        try {
            Util.connection.setAutoCommit(false);
            PreparedStatement preparedStatement = Util.connection.prepareStatement("DELETE FROM users.user where id");
            preparedStatement.executeUpdate();
            System.out.println("User удален");
            Util.connection.commit();
        } catch (SQLException e) {
            try {
                Util.connection.rollback();
                Util.connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Ошибка удаления user");
        }
        finally {
            try {
                Util.connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error setAutoCommit(true)");
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age from users.user";

        try {
            Util.connection.setAutoCommit(true);
            PreparedStatement preparedStatement = Util.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUser.add(user);
            }
            Util.connection.setAutoCommit(false);
        } catch (SQLException e) {
            try {
                Util.connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Ошибка выделения users");
        }
        finally {
            try {
                Util.connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error setAutoCommit(true)");
            }
        }
        return allUser;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users.user";
        try {
            Util.connection.setAutoCommit(false);
            PreparedStatement preparedStatement = Util.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Таблица очищена");
            Util.connection.commit();
        } catch (SQLException e) {
            try {
                Util.connection.rollback();
                Util.connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException("Ошибка очистки таблицы");
        }
        finally {
            try {
                Util.connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error setAutoCommit(true)");
            }
        }
    }
    @Override
    public void close() {
        try {
            if (Util.connection != null) {
                Util.connection .close();
                System.out.println("Connection is closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}