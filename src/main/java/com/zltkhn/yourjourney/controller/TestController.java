/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.controller;

import com.zltkhn.yourjourney.entities.TestModel;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.repository.UserRepository;
import com.zltkhn.yourjourney.service.Crypter;
import java.awt.SystemColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rtmss
 */
@RestController
public class TestController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private Crypter crypter;
    
    @RequestMapping(value = {"/"})
    public TestModel index(){
        return new TestModel(System.currentTimeMillis(), "Hail");
    }
    
}
