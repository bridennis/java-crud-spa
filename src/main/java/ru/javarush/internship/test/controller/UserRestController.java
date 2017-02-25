package ru.javarush.internship.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javarush.internship.test.model.User;
import ru.javarush.internship.test.service.UserService;

import java.util.List;

@Transactional
@RestController
public class UserRestController {

    private UserService userService;

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /* READ BY PAGE + FILTER */
    @RequestMapping(value = "/user/", params = { "offset", "limit", "filter" }, method = RequestMethod.GET)
    public ResponseEntity<Object> listAllUsers(@RequestParam("offset") long offset, @RequestParam("limit") long limit, @RequestParam("filter") String filter) {

        long count  = userService.getUserCounter();

        List<User> users = userService.getUserList(offset, limit, filter);

        class ResponseUsers {
            public long count = 0L;
            public long countFiltered = 0L;
            public List<User> items = null;

            public ResponseUsers(long count, long countFiltered, List<User> items) {
                this.count = count;
                this.countFiltered = countFiltered;
                this.items = items;
            }
        }

        ResponseUsers responseUsers = new ResponseUsers(count, userService.getUserFilteredCounter(filter), users);

        return new ResponseEntity<Object>(responseUsers, HttpStatus.OK);
    }

    /* READ BY Id */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /* CREATE */
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        userService.addUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    /* UPDATE */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        User currentUser = userService.getUserById(id);

        if (currentUser == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setAdmin(user.isAdmin());

        userService.updateUser(currentUser);

        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    /* DELETE */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {

        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.delUser(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
