package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            String URL = "jdbc:mysql://localhost:3306/mydbtest";
            String USER = "root";
            String PASS = "root";
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();

            String sqlcmd = "create table users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(65), lastname VARCHAR(65), age TINYINT)";

            try {
                 connection = DriverManager.getConnection(URL, USER, PASS);
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate(sqlcmd);
                    System.out.println("Таблица создана");
            } catch (Exception e) {
                System.out.println("Ошибка создания таблицы");
            }

        } catch (Exception e) {
            System.out.println("Ошибка ссылок");
            System.out.println(e.getMessage());
        }

    }

    public void dropUsersTable() {
        try {
            String URL = "jdbc:mysql://localhost:3306/mydbtest";
            String USER = "root";
            String PASS = "root";
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();

            String sqlcmd = "drop table users";

            try {
                connection = DriverManager.getConnection(URL, USER, PASS);
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(sqlcmd);
                System.out.println("Таблица удалена");
            } catch (Exception e) {
                System.out.println("Ошибка удаления таблицы");
            }

        } catch (Exception e) {
            System.out.println("Ошибка ссылок");
            System.out.println(e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement ps = null;
        String sql = "INSERT INTO users (name, lastname, age) values (?, ?, ?)" ;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);

            ps.executeUpdate();
            System.out.println("User c именем "+name+ " добавлен в базу данных");
        } catch (Exception e) {
            System.out.println("Ошибка создания юзера");
        } finally {
            if (ps != null) { try { ps.close();
               // System.out.println( "База закрыта");
            } catch (Exception e) {
                System.out.println("База не закрыта"); } }
           // if (connection != null) { try { connection.close();
           //     System.out.println("Коннект закрыт");} catch (Exception e) {
           //     System.out.println("Коннект не закрыт");
           // }
           // }
        }
    }

    public void removeUserById(long id) {
        PreparedStatement ps = null;

        String sql = "DELETE FROM users WHERE id = ?";

        try {
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
            System.out.println("User "+id+ " удален из базы");
        } catch (Exception e){
            System.out.println("Ошибка удаления");
        } finally {
            if (ps != null) { try { ps.close(); } catch (Exception e) {
                System.out.println("База не закрыта");
            }}
          //  if (connection != null) { try { connection.close(); } catch (Exception e) {
          //      System.out.println("Коннект не закрыт");
          //  }}
        }
    }

    public List<User> getAllUsers() {
        List<User> userArrayList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        Statement stmt = null;

        try {
            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                userArrayList.add(user);
            }
                for (User user : userArrayList) {
                    System.out.println(user.toString());
                }
            //System.out.println("Люди закончились");
        } catch (Exception e) {
            System.out.println("Ошибка вывода людей");
        } finally {
            if (stmt != null) { try { stmt.close(); } catch (Exception e) {
                System.out.println("STMT не закрыт");
            }}
           // if (connection != null) { try { connection.close(); } catch (Exception e) {
          //     System.out.println("Коннект не закрыт");
           // }}
        }
        return userArrayList;
    }

    public void cleanUsersTable() {
        PreparedStatement ps = null;

        String sql = "truncate table users";

        try {
             ps = connection.prepareStatement(sql);
            ps.executeUpdate(sql);
            System.out.println("Все удалены");
        } catch (Exception e){
            System.out.println("Ошибка удаления всех");
        } finally {
            if (ps != null) { try { ps.close(); } catch (Exception e) {
               System.out.println("База не закрыта");
            }}
        //  if (connection != null) { try { connection.close(); } catch (Exception e) {
       //         System.out.println("Коннект не закрыт");
       //  }}
       }

    }
}
