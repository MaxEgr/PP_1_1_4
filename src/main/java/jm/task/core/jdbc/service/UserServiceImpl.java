package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDaoJDBCImpl userDaoJDBC;
    private UserDaoJDBCImpl daoHibernate;

    public UserServiceImpl() {
        this.userDaoJDBC = new UserDaoJDBCImpl();
        this.daoHibernate = new UserDaoJDBCImpl();
    }

    public void createUsersTable() {
        daoHibernate.createUsersTable();
    }

    public void dropUsersTable() {
        daoHibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        daoHibernate.saveUser(name,lastName,age);
    }

    public void removeUserById(long id) {
        daoHibernate.removeUserById(id);
    }

    public List<User> getAllUsers() {

        return daoHibernate.getAllUsers();
    }

    public void cleanUsersTable() {
        daoHibernate.cleanUsersTable();
    }
    @Override
    public void close() {
        daoHibernate.close();
    }
}