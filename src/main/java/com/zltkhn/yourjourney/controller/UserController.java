/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.controller;

import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.form.EditProfileForm;
import com.zltkhn.yourjourney.service.api.ErrorCodes;
import com.zltkhn.yourjourney.service.api.exception.InvalidFormException;
import com.zltkhn.yourjourney.service.api.UserService;
import com.zltkhn.yourjourney.service.api.exception.ContentTypeOfMultipartFileIsNotImageException;
import com.zltkhn.yourjourney.service.api.exception.DeadAccessTokenException;
import com.zltkhn.yourjourney.service.api.exception.NotFoundException;
import com.zltkhn.yourjourney.service.api.exception.UserNotFoundException;
import com.zltkhn.yourjourney.service.api.response.ApiResult;
import com.zltkhn.yourjourney.service.api.response.EditAvatarResult;
import com.zltkhn.yourjourney.service.api.response.ProfileResult;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.zltkhn.yourjourney.service.StorageService;

/**
 *
 * @author rtmss
 */
@RestController
@RequestMapping(value = "/api")
public class UserController {
    
    
    public static final String USER_PHOTO_PREFIX = "userphoto";
    
    @Autowired
    private ErrorCodes errorCodes;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private StorageService multipartFileService;
    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ApiResult profile(@RequestHeader(name = "token", required = true) String token) {
        System.out.println("PROFILE");
        
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        
        try {
            ProfileResult profileResult = userService.getPojoByAccessToken(token);
            if (profileResult != null) {
                System.out.println(profileResult.toString());
                apiResult.setBody(profileResult);
            } else {
                apiResult.setCode(errorCodes.getNotFound());
            }
        } catch (DeadAccessTokenException ex) {
            apiResult.setCode(errorCodes.getInvalidOrOldAccessToken());
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserNotFoundException ex) {
            apiResult.setCode(errorCodes.getNotFound());
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return apiResult;
    }
    
    @RequestMapping(value = "/profile", method = RequestMethod.PUT) 
    public ApiResult editProfile(@RequestHeader(name = "token", required = true) String token,
            @RequestBody EditProfileForm editProfileForm) {
        
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        
        try {
            userService.editUser(token, editProfileForm);
        } catch (DeadAccessTokenException ex) {
            apiResult.setCode(errorCodes.getInvalidOrOldAccessToken());
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserNotFoundException ex) {
            apiResult.setCode(errorCodes.getNotFound());
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormException ex) {
            apiResult.setCode(errorCodes.getInvalidForm());
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return apiResult;
    }
    
    @RequestMapping(value = USER_PHOTO_PREFIX)
    public byte[] getUserPhoto(@RequestParam(required = true) String name) {
        try {
            return multipartFileService.get(name);
        } catch (NotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return new byte[0];
        }
    }
    
    @RequestMapping(value = "/profile/avatar", method = RequestMethod.POST) 
    public ApiResult editAvatar(@RequestHeader(name = "token", required = true) String token,
            @RequestParam("image") MultipartFile image) {
        
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        
        try {
            User user = userService.getByAccessToken(token);
            
            String avatarName = "api/" + USER_PHOTO_PREFIX + "?name=" + userService.updateAvatar(user, image);
            
            apiResult.setBody(new EditAvatarResult(avatarName));
            
        } catch (DeadAccessTokenException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getInvalidOrOldAccessToken());
        } catch (UserNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getNotFound());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getInvalidForm());
        } catch (ContentTypeOfMultipartFileIsNotImageException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getInvalidForm());
        }
        
        return apiResult;
        
    }
    
    @RequestMapping(value = "/tourists/{id}")
    public ApiResult tourist(@PathVariable("id") long id, @RequestParam(name = "places", required = false, defaultValue = "0") int places) {
        
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
                
        try {
            ProfileResult profileResult = userService.getPojoById(id, (places == 1));
            if (profileResult != null) {
                apiResult.setBody(profileResult);
            } else {
                apiResult.setCode(errorCodes.getNotFound());
            }
        } catch (UserNotFoundException ex) {
            apiResult.setCode(errorCodes.getNotFound());
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return apiResult;
    }
    
    
    
}
