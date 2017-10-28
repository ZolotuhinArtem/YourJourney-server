/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api;

import com.zltkhn.yourjourney.service.api.exception.UserNotFoundException;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.service.api.exception.DeadAccessTokenException;
import com.zltkhn.yourjourney.service.api.response.UserResult;
import org.springframework.stereotype.Service;

/**
 *
 * @author rtmss
 */

public interface UserService {
    
    User getById(Long id) throws UserNotFoundException;
    
    User getByAccessToken(String accessToken) throws DeadAccessTokenException;
    
    UserResult getPojoById(Long id) throws UserNotFoundException;
    
    UserResult getPojoByAccessToken(String accessToken) throws DeadAccessTokenException;
}
