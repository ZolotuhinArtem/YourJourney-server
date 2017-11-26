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
import com.zltkhn.yourjourney.form.validator.LoginFormValidator;
import com.zltkhn.yourjourney.form.validator.RegistrationFormValidator;
import com.zltkhn.yourjourney.repository.UserRepository;
import com.zltkhn.yourjourney.repository.UserTokenRepository;
import com.zltkhn.yourjourney.service.Crypter;
import com.zltkhn.yourjourney.service.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rtmss
 */

public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserTokenRepository userTokenRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TokenGenerator tokenGenerator;
    
    @Autowired
    private LoginFormValidator loginFormValidator;
    
    @Autowired
    private Crypter crypter;
    
    @Autowired
    private RegistrationFormValidator registrationFormValidator;
        
    private long accessTokenLifeTime;

    public AuthServiceImpl(long accessTokenLifeTime) {
        this.accessTokenLifeTime = accessTokenLifeTime;
    }
    
    @Override
    public User getUserByAccessToken(String accessToken) throws DeadAccessTokenException {
        UserToken token = userTokenRepository.findOneByAccessToken(accessToken);
        if (token == null) {
            throw new DeadAccessTokenException("token doesn't exist");
        } else {
            if (token.getExpiresIn() < System.currentTimeMillis()) {
                throw new DeadAccessTokenException("token is old");
            } else {
                return token.getUser();
            }
        }
    }

    @Override
    public UserToken getToken(String refreshToken) throws DeadAccessTokenException {
        UserToken token = userTokenRepository.findOneByRefreshToken(refreshToken);
        if (token == null) {
            throw new DeadAccessTokenException("tokenNotFound");
        } else {
            refreshToken(token);
            return token;
        }
    }

    @Override
    public UserToken getToken(LoginForm loginForm) throws IncorrectLoginDataException {
        int validation = loginFormValidator.validate(loginForm);
        if (validation == 0) {
            User user = userRepository.findOneByEmailAndPasswordCrypt(loginForm.getEmail(), 
                    crypter.crypt(loginForm.getPassword()));
            if (user != null) {
                UserToken token = user.getToken();
                if (token  != null) {
                    refreshToken(token);
                    return token;
                } else {
                    
                    token = genetareToken(user);
                    return token;
                }
            } else {
                throw new IncorrectLoginDataException("user or password incorrect");
            }
            
        } else {
            throw new IncorrectLoginDataException("Login form is invalid");
        }
    }

    @Override
    public UserToken registration(RegistrationForm registrationForm) throws IncorrectRegistrationFormException, UserWithSameEmailAlreadyExistsException {
        
        boolean validation = registrationFormValidator.validate(registrationForm);
        if (!validation) {
            throw new IncorrectRegistrationFormException(registrationForm.toString());
        } else {
            User user = userRepository.findOneByEmail(registrationForm.getEmail());
            if (user != null) {
                throw new UserWithSameEmailAlreadyExistsException();
            } else {
                user = registrationForm.generateUser(crypter);
                user = userRepository.save(user);
                UserToken token = genetareToken(user);
                return token;
            }
        }
        
    }

    @Override
    public boolean isAccessTokenActive(String accessToken) {
        UserToken token = userTokenRepository.findOneByAccessToken(accessToken);
        if (token == null) {
            return false;
        } else {
            return token.getExpiresIn() > System.currentTimeMillis();
        }
    }
    
    private void refreshToken(UserToken token) {
        token.setAccessToken(tokenGenerator.generate());
        token.setRefreshToken(tokenGenerator.generate());
        token.setExpiresIn(System.currentTimeMillis() + accessTokenLifeTime);
        userTokenRepository.save(token);
    }
    
    //If not exists in db
    private UserToken genetareToken(User user) {
        UserToken token = new UserToken();
        token.setUser(user);
        token.setAccessToken(tokenGenerator.generate());
        token.setRefreshToken(tokenGenerator.generate());
        token.setExpiresIn(System.currentTimeMillis() + accessTokenLifeTime);
        token = userTokenRepository.save(token);
        return token;
    }
    
}
