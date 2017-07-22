package com.mithu.springbootDemo.controllers;

import com.mithu.springbootDemo.model.User;
import com.mithu.springbootDemo.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mithu on 25/6/17.
 */
@RestController
public class UserController {

    @Autowired
    UserMongoRepository userMongoRepository;

    @RequestMapping("/listUsers")
    public List<User> list(Model model) {
        List<User> userList = new ArrayList<>();
        userMongoRepository.findAll().iterator().forEachRemaining(userList::add);
        return userList;
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@RequestParam(required = false) String id) {
        User user;
        if (id != null) {
            user = userMongoRepository.findOne(id);
            return ResponseEntity.ok(user);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseEntity saveUser(@RequestBody User user) {
        Random random = new Random();
        int value = random.nextInt(2000);
        if (user.getId() == null) {
            user.setId(String.valueOf(value));
            userMongoRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User saved");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist");
        }
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable String userId) {
        User existingUser = userMongoRepository.findOne(userId);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");

        } else {
            user.setId(userId);
            userMongoRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("updated user");
        }

    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable String userId) {
        User existingUser = userMongoRepository.findOne(userId);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");

        } else {
            userMongoRepository.delete(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete");

        }

    }

}
