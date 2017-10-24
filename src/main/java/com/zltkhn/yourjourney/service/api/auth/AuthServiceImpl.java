/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.auth;

import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.entities.UserToken;
import com.zltkhn.yourjourney.form.LoginForm;
import com.zltkhn.yourjourney.form.UserForm;
import com.zltkhn.yourjourney.repository.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rtmss
 */
@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserTokenRepository userTokenRepository;
    
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserToken getToken(LoginForm loginForm) throws IncorrectLoginDataException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserToken registration(UserForm userForm) throws IncorrectUserFormException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
