package ru.javarush.internship.test.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import ru.javarush.internship.test.model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    public void delUser(long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.load(User.class, id);
        if (user != null) {
            session.delete(user);
        }
    }

    public User getUserById(long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @SuppressWarnings("unchecked")
    public long getUserCounter() {

        return (Long) sessionFactory.getCurrentSession().createQuery("select count(*) from User").iterate().next();
    }

    @SuppressWarnings("unchecked")
    public long getUserFilteredCounter(String filter) {

        return (Long) getFilteredSession(filter).createQuery("select count(*) from User").iterate().next();
    }

    @SuppressWarnings("unchecked")
    public List<User> getUserList(long offset, long limit, String filter) {

        Session session = getFilteredSession(filter);

        Query q = session.createQuery("from User");
        q.setFirstResult((int) offset);
        q.setMaxResults((int) limit);

        return q.list();
    }

    private Session getFilteredSession(String filter) {
        try {
            Session session = sessionFactory.getCurrentSession();

            JSONObject jsonObj = new JSONObject(filter);

            if (jsonObj.has("name") && jsonObj.get("name") != null && !jsonObj.get("name").equals("")) {
                session.enableFilter("byName").setParameter("name", "%" + jsonObj.get("name") + "%");
            }

            if (jsonObj.has("age") && !jsonObj.isNull("age") && !jsonObj.get("age").equals("")) {
                session.enableFilter("byAge").setParameter("age", jsonObj.get("age"));
            }

            if (jsonObj.has("admin") && !jsonObj.isNull("admin") && !jsonObj.get("admin").equals("")) {
                if (jsonObj.get("admin").equals("true")) {
                    session.enableFilter("byAdmin").setParameter("admin", true);
                } else if (jsonObj.get("admin").equals("false")) {
                    session.enableFilter("byAdmin").setParameter("admin", false);
                }
            }

            return session;

        } catch (Exception e) {
            return null;
        }

    }
}
