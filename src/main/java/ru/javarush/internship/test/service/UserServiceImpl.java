package ru.javarush.internship.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.internship.test.dao.UserDao;
import ru.javarush.internship.test.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void addUser(User user) {
        this.userDao.addUser(user);
    }

    @Transactional
    public void updateUser(User user) {
        this.userDao.updateUser(user);
    }

    @Transactional
    public void delUser(long id) {
        this.userDao.delUser(id);
    }

    @Transactional
    public User getUserById(long id) {
        return this.userDao.getUserById(id);
    }

    @Transactional
    public long getUserCounter() {
        return this.userDao.getUserCounter();
    }

    @Transactional
    public long getUserFilteredCounter(String filter) {
        return this.userDao.getUserFilteredCounter(filter);
    }

    @Transactional
    public List<User> getUserList(long offset, long limit, String filter) {
        return this.userDao.getUserList(offset, limit, filter);
    }

}
