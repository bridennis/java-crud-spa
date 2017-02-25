package ru.javarush.internship.test.service;

import ru.javarush.internship.test.model.User;
import java.util.List;

public interface UserService {
    void addUser(User user);

    void updateUser(User user);

    void delUser(long id);

    User getUserById(long id);

    long getUserCounter();

    long getUserFilteredCounter(String filter);

    List<User> getUserList(long offset, long limit, String filter);

}
