/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.controller;

import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.entities.UserToken;
import com.zltkhn.yourjourney.form.LoginForm;
import com.zltkhn.yourjourney.service.api.ErrorCodes;
import com.zltkhn.yourjourney.service.api.PlaceService;
import com.zltkhn.yourjourney.service.api.UserService;
import com.zltkhn.yourjourney.service.api.exception.DeadAccessTokenException;
import com.zltkhn.yourjourney.service.api.exception.IncorrectLoginDataException;
import com.zltkhn.yourjourney.service.api.exception.UserNotFoundException;
import com.zltkhn.yourjourney.service.api.response.ApiResult;
import com.zltkhn.yourjourney.service.api.response.PlacesResult;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rtmss
 */
@RestController
@RequestMapping(value = "/api")
public class PlaceController {
    
    
    @Autowired
    private ErrorCodes errorCodes;
    
    @Autowired
    private PlaceService placeService;
    
    @Autowired
    private UserService userService;
    
    
    @RequestMapping(value = "/places", method = RequestMethod.GET)
    public ApiResult places(@RequestParam(name = "country", required = true) String country,
            @RequestParam(name = "city", required = true) String city,
            @RequestHeader(name = "token", required = false) String token) {
        
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        
        User user = null;
        
        if (token != null) {
            try {
                user = userService.getByAccessToken(token);
            } catch (DeadAccessTokenException ex) {
                Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UserNotFoundException ex) {
                Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        PlacesResult placesResult = placeService.getShortInfoPlacesByLocation(country, city, user);
        
        apiResult.setBody(placesResult);
        
        return apiResult;
    }
}