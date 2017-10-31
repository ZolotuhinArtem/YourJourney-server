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
import com.zltkhn.yourjourney.service.api.auth.AuthService;
import com.zltkhn.yourjourney.service.api.exception.DeadAccessTokenException;
import com.zltkhn.yourjourney.service.api.exception.UserNotFoundException;
import com.zltkhn.yourjourney.service.api.response.ProfileResult;
import com.zltkhn.yourjourney.tools.UserToProfileResultConverter;
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
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserToProfileResultConverter userToProfileResultConverter;
    
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
    public User getByAccessToken(String accessToken) throws DeadAccessTokenException, UserNotFoundException {
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
                    throw new UserNotFoundException();
                }
            } else {
                throw new DeadAccessTokenException();
            }
        } else {
            throw new DeadAccessTokenException();
        }
    }

    @Override
    public ProfileResult getPojoById(Long id, boolean showPlaces) throws UserNotFoundException {
        
        User user = userRepository.findOne(id);
        
        if (user != null) {
            ProfileResult profileResult = userToProfileResultConverter.convert(user, showPlaces, false, true);
            
            if (profileResult != null) {
                return profileResult;
            } else {
                throw new UserNotFoundException(ProfileResult.class.getName() + " is null");
            }
        } else {
            throw new UserNotFoundException();
        }
        
    }

    @Override
    public ProfileResult getPojoByAccessToken(String accessToken) throws DeadAccessTokenException, UserNotFoundException {
        if (authService.isAccessTokenActive(accessToken)) {
            UserToken userToken = userTokenRepository.findOneByAccessToken(accessToken);
            if (userToken != null) {
                
                User user = userToken.getUser();
                
                if (user != null) {
                    
                    ProfileResult profileResult = userToProfileResultConverter.convert(user, true, true, false);
                    
                    if (profileResult != null) {
                        
                        return profileResult;
                        
                    } else {
                        throw new UserNotFoundException();
                    }
                    
                } else {
                    throw new UserNotFoundException();
                }
                
            } else {
                throw new DeadAccessTokenException();
            }
        } else {
            throw new DeadAccessTokenException();
        }
        
    }
    
}
