package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static final UserService userService = new UserServiceImpl();
    public static final User user1 = new User("Max", "Egorov" , (byte) 32 );
    public static final User user2 = new User("Marat", "Basharov" , (byte) 27);
    public static final User user3 = new User("Maria", "Veselova" , (byte) 18 );
    public static final User user4 = new User("Leonid", "Komrad" , (byte) 22 );

    public static void main(String[] args) {

        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());

        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());

        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}