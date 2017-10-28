/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.controller;

import com.zltkhn.yourjourney.service.api.response.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rtmss
 */
@RestController
@RequestMapping(value = "/api")
public class UserController {
    
    @Autowired
    private AuthController authController;
    
    @RequestMapping(value = "/profile")
    public ApiResult profile(@RequestHeader(name = "token", required = true) String token,
            @RequestParam(name = "places", required = false, defaultValue = "0") int places) {
        
        
        
        return null;
    }
    
}
