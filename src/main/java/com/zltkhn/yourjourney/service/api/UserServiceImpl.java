/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api;

import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.entities.UserToken;
import com.zltkhn.yourjourney.repository.UserRepository;
import com.zltkhn.yourjourney.repository.UserTokenRepository;
import com.zltkhn.yourjourney.service.api.exception.DeadAccessTokenException;
import com.zltkhn.yourjourney.service.api.exception.UserNotFoundException;
import com.zltkhn.yourjourney.service.api.response.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rtmss
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserTokenRepository userTokenRepository;
    
    @Override
    public User getById(Long id) throws UserNotFoundException {
        if (id == null) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findOne(id);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User getByAccessToken(String accessToken) throws DeadAccessTokenException {
        if (accessToken == null) {
            throw  new DeadAccessTokenException();
        }
        
        UserToken token = userTokenRepository.findOneByAccessToken(accessToken);
        if (token != null) {
            if (token.getExpiresIn() > System.currentTimeMillis()) {
                User user = token.getUser();
                if (user != null) {
                    return user;
                } else {
                    throw new DeadAccessTokenException();
                }
            } else {
                throw new DeadAccessTokenException();
            }
        } else {
            throw new DeadAccessTokenException();
        }
    }

    @Override
    public UserResult getPojoById(Long id) throws UserNotFoundException {
        return null;
    }

    @Override
    public UserResult getPojoByAccessToken(String accessToken) throws DeadAccessTokenException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
