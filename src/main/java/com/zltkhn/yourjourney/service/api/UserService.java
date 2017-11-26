/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api;

import com.zltkhn.yourjourney.service.api.exception.InvalidFormException;
import com.zltkhn.yourjourney.service.api.exception.UserNotFoundException;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.form.EditProfileForm;
import com.zltkhn.yourjourney.service.api.exception.DeadAccessTokenException;
import com.zltkhn.yourjourney.service.api.response.ProfileResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rtmss
 */

public interface UserService {
    
    User getById(Long id) throws UserNotFoundException;
    
    User getByAccessToken(String accessToken) throws DeadAccessTokenException, UserNotFoundException;
    
    ProfileResult getPojoById(Long id, boolean showPlaces) throws UserNotFoundException;
    
    ProfileResult getPojoByAccessToken(String accessToken) throws DeadAccessTokenException, UserNotFoundException;
    
    void editUser(String accessToken, EditProfileForm editProfileForm) throws DeadAccessTokenException, UserNotFoundException, InvalidFormException;
    
    String updateAvatar(User user, MultipartFile multipartFile) throws DeadAccessTokenException;
}
