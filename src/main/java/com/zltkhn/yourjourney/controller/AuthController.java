/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.controller;

import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.entities.UserToken;
import com.zltkhn.yourjourney.form.LoginForm;
import com.zltkhn.yourjourney.form.RegistrationForm;
import com.zltkhn.yourjourney.repository.UserRepository;
import com.zltkhn.yourjourney.repository.UserTokenRepository;
import com.zltkhn.yourjourney.service.Crypter;
import com.zltkhn.yourjourney.service.api.ErrorCodes;
import com.zltkhn.yourjourney.service.api.response.ApiResult;
import com.zltkhn.yourjourney.service.api.auth.AuthService;
import com.zltkhn.yourjourney.service.api.exception.DeadAccessTokenException;
import com.zltkhn.yourjourney.service.api.exception.IncorrectLoginDataException;
import com.zltkhn.yourjourney.service.api.exception.IncorrectRegistrationFormException;
import com.zltkhn.yourjourney.service.api.exception.UserWithSameEmailAlreadyExistsException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserTokenRepository userTokenRepository;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private ErrorCodes errorCodes;
    
    @Autowired
    private Crypter crypter;
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult login(@RequestBody LoginForm loginForm) {
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        try {
            UserToken userToken = authService.getToken(loginForm);
            if (userToken != null) {
                apiResult.setBody(userToken);
            } else {
                // W T F ?
                apiResult.setCode(errorCodes.getNotFound());
            }
        } catch (IncorrectLoginDataException ex) {
            apiResult.setCode(errorCodes.getInvalidLoginOrPassword());
        }
        
        return apiResult;
    }
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ApiResult registration(@RequestBody RegistrationForm registrationForm) {
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        try {
            UserToken token = authService.registration(registrationForm);
            if (token != null) {
                apiResult.setBody(token);
            } else {
                // W T F?
                apiResult.setCode(errorCodes.getNotFound());
            }
        } catch (IncorrectRegistrationFormException ex) {
            apiResult.setCode(errorCodes.getInvalidForm());
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (UserWithSameEmailAlreadyExistsException ex) {
            apiResult.setCode(errorCodes.getUserWithSameLoginAlreadyExists());
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return apiResult;
    }
    
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ApiResult updateToken(@RequestParam(name = "refresh_token",
            required = true) String refreshToken) {
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        if (refreshToken != null) {
            try {
                UserToken token = authService.getToken(refreshToken);
                if (token != null) {
                    apiResult.setBody(token);
                } else {
                    //W T F?
                    apiResult.setCode(errorCodes.getNotFound());
                }
            } catch (DeadAccessTokenException ex) {
                apiResult.setCode(errorCodes.getInvalidOrOldAccessToken());
                Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            apiResult.setCode(errorCodes.getInvalidRequest());
        }
        return apiResult;
    } 
}
