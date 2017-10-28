/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.auth;

import com.zltkhn.yourjourney.service.api.exception.DeadAccessTokenException;
import com.zltkhn.yourjourney.service.api.exception.IncorrectLoginDataException;
import com.zltkhn.yourjourney.service.api.exception.UserWithSameEmailAlreadyExistsException;
import com.zltkhn.yourjourney.service.api.exception.IncorrectRegistrationFormException;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.entities.UserToken;
import com.zltkhn.yourjourney.form.LoginForm;
import com.zltkhn.yourjourney.form.RegistrationForm;
import org.springframework.stereotype.Service;

/**
 *
 * @author rtmss
 */
@Service
public interface AuthService {
    
    boolean isAccessTokenActive(String accessToken);
    
    User getUserByAccessToken(String accessToken) throws DeadAccessTokenException;
    
    UserToken getToken(String refreshToken) throws DeadAccessTokenException;
    
    UserToken getToken(LoginForm loginForm) throws IncorrectLoginDataException;
    
    UserToken registration(RegistrationForm registrationForm) throws IncorrectRegistrationFormException,
                                                                UserWithSameEmailAlreadyExistsException;
}
