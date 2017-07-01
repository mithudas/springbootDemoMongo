package com.mithu.springbootDemo.controllers;

import com.mithu.springbootDemo.model.User;
import com.mithu.springbootDemo.repository.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mithu on 25/6/17.
 */
@Controller
public class UserController {

    @Autowired
    UserMongoRepository userMongoRepository;

    @RequestMapping("/home")
    public String list(Model model) {

        System.out.printf("index>>>>");
        model.addAttribute("userList", userMongoRepository.findAll());
        return "home";
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public  String createUser( Model model){
        model.addAttribute("firstName", "mithu");
        model.addAttribute("lastName", "");
        model.addAttribute("gender", "");
        model.addAttribute("age", "");
        return "/user/addUser";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user) {

        System.out.println("inside adduser");
        userMongoRepository.save(user);
        return "redirect:home";
    }


    public String updateUser(){
        return  "/user/addUser";
    }

}
