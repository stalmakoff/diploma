package com.stalmakof.diploma.controller;

import com.stalmakof.diploma.common.Utility;
import com.stalmakof.diploma.models.User;
import com.stalmakof.diploma.repository.UserRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;








    @RequestMapping(value = "/api/getAllUsers", method = RequestMethod.POST)
    public List<User> getUsers() {
        return this.getAllUsers();
    }


    @RequestMapping(value = "/api/editUser", method = RequestMethod.POST)
    public User editUser(@RequestBody String data) {
        try{
            JSONObject userData = this.parseData(data);
            User user = this.getUser(Long.parseLong(userData.get("user_id").toString()));
            return this.editUser(user);
        } catch (Exception e) {
            return null;
        }
    }






    private JSONObject parseData(String data) {
        try {
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }






    public User getUser(String username, String password) {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(Utility.md5(password))) {
                return user;
            }
        }
        return null;
    }


    public User getUser(Long id) {
        return userRepository.findOne(id);
    }


    public List<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<User> data = new ArrayList<User>();
        for (User user : users) {
            data.add(user);
        }
        return data;
    }


    public User editUser(User user) {
        return userRepository.save(user);
    }

}
