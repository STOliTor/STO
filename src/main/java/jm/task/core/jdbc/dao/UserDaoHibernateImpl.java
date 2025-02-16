package jm.task.core.jdbc.dao;

import com.mysql.cj.xdevapi.SessionFactory;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {

    Session session = HibernateUtil.getSessionFactory().openSession();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS User " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Ошибка создания таблицы");;
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session.beginTransaction();
            String hql = "drop table if exists User";
            Query query = session.createSQLQuery(hql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка удаоения таблицы");;
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Ошибка создания юзера");;
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();
            session.createQuery("delete from User where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка удаления юзера");
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        session.beginTransaction();

        users = session.createQuery("FROM User", User.class).getResultList();

        session.getTransaction().commit();

        for (User user : users) {
        System.out.println(user.toString());
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();
            Query query = session.createQuery("DELETE from User");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Ошибка удаления всех");
        }
    }
}
