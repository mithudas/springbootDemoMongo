package com.mithu.springbootDemo.controllers;

import com.mithu.springbootDemo.model.User;
import com.mithu.springbootDemo.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mithu on 25/6/17.
 */
@Controller
public class UserController {

    @Autowired
    UserMongoRepository userMongoRepository;

    @RequestMapping("/listUsers")
    public String list(Model model) {
        model.addAttribute("userList", userMongoRepository.findAll());
        return "/user/list";
    }

    @RequestMapping(value = {"/createUser", "/updateUser"}, method = RequestMethod.GET)
    public String createUser(Model model, @RequestParam(required = false) String id) {
        User user;
        if (id != null) {
            user = userMongoRepository.findOne(id);
            System.out.println("${user}" + user.toString());

        } else {
            user = new User();
        }
        model.addAttribute("user", user);
        return "/user/createOrUpdate";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user) {
        String userId = user.getId();
        if (userId != null) {
            User existingUser = userMongoRepository.findOne(userId);
            existingUser = user;
            userMongoRepository.save(existingUser);
        } else {
            userMongoRepository.save(user);
        }
        return "redirect:listUsers";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser() {
        return "/user/createOrUpdate";
    }

}
