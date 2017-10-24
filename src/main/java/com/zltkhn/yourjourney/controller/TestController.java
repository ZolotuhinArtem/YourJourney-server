/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.controller;

import com.zltkhn.yourjourney.entities.TestModel;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.entities.UserToken;
import com.zltkhn.yourjourney.form.LoginForm;
import com.zltkhn.yourjourney.repository.UserRepository;
import com.zltkhn.yourjourney.repository.UserTokenRepository;
import com.zltkhn.yourjourney.service.Crypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rtmss
 */
@RestController(value = "/api")
public class TestController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserTokenRepository userTokenRepository;
    
    @Autowired
    private Crypter crypter;
    
    @RequestMapping(value = {"/"})
    public TestModel index(){
        String result = "";
        User user = userRepository.findOne(1L);
        result += "" + user + "\n";
        if (user != null) {
            UserToken userToken = userTokenRepository.findOneByUserId(1L);
            result += "" + userToken;
        }
        return new TestModel(System.currentTimeMillis(), result);
    }
    
    @RequestMapping(value = {"/login"})
    public LoginForm login(@RequestBody LoginForm loginForm) {
        return loginForm;
    }
    
}
