/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.controller;

import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.form.CommentForm;
import com.zltkhn.yourjourney.service.api.ErrorCodes;
import com.zltkhn.yourjourney.service.api.PlaceService;
import com.zltkhn.yourjourney.service.api.UserService;
import com.zltkhn.yourjourney.service.api.exception.DeadAccessTokenException;
import com.zltkhn.yourjourney.service.api.exception.InvalidFormException;
import com.zltkhn.yourjourney.service.api.exception.NotFoundException;
import com.zltkhn.yourjourney.service.api.exception.PermissionDeniedException;
import com.zltkhn.yourjourney.service.api.exception.PlaceNotFoundException;
import com.zltkhn.yourjourney.service.api.exception.UserNotFoundException;
import com.zltkhn.yourjourney.service.api.response.ApiResult;
import com.zltkhn.yourjourney.service.api.response.PlaceCommentsResult;
import com.zltkhn.yourjourney.service.api.response.PlaceResult;
import com.zltkhn.yourjourney.service.api.response.PlacesShortResult;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
        
        PlacesShortResult placesResult = placeService.getShortInfoPlacesByLocation(country, city, user);
        
        apiResult.setBody(placesResult);
        
        return apiResult;
    }
    
    @RequestMapping(value = "/places/{id}/comments", method = RequestMethod.POST)
    public ApiResult writeComment(@PathVariable("id") long id, 
            @RequestHeader(name = "token", required = true) String token, 
            @RequestBody CommentForm commentForm) {
        
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        
        try {
            User user = userService.getByAccessToken(token);
            
            apiResult.setBody(placeService.writeComment(id, user, commentForm));
            
        } catch (DeadAccessTokenException ex) {
            Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getInvalidOrOldAccessToken());
        } catch (UserNotFoundException | PlaceNotFoundException ex) {
            Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getNotFound());
        } catch (PermissionDeniedException ex) {
            Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getPermissionDenied());
        } catch (InvalidFormException ex) {
            Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getInvalidForm());
        }
        
        return apiResult;
        
    }
    
    @RequestMapping(value = "/places/{id}/comments/{idc}", method = RequestMethod.DELETE)
    public ApiResult removeComment(@PathVariable("id") long placeId, 
            @PathVariable("idc") long commentId, 
            @RequestHeader(name = "token", required = true) String token) {
        
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        
        try {
            User user = userService.getByAccessToken(token);
            
            placeService.removeComment(commentId, user);
            
        } catch (DeadAccessTokenException ex) {
            Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getInvalidOrOldAccessToken());
        } catch (PermissionDeniedException ex) {
            Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getPermissionDenied());
        } catch (NotFoundException | IllegalArgumentException | UserNotFoundException ex) {
            Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getNotFound());
        }
        
        return apiResult;
        
    }
    
    @RequestMapping(value = "/places/{id}/like", method = RequestMethod.POST)
    public ApiResult like(@PathVariable("id") long id, 
            @RequestHeader(name = "token", required = true) String token) {
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        
        User user;
        try {
            user = userService.getByAccessToken(token);
            apiResult.setBody(placeService.like(id, user));
            
        } catch (DeadAccessTokenException ex) {
            Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getInvalidOrOldAccessToken());
        } catch (UserNotFoundException | NotFoundException | IllegalArgumentException ex) {
            Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getNotFound());
        } catch (PermissionDeniedException ex) {
            Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            apiResult.setCode(errorCodes.getPermissionDenied());
        }
        
        return apiResult;
        
    }
    
    @RequestMapping(value = "/places/{id}/comments", method = RequestMethod.GET)
    public ApiResult comments(@PathVariable("id") long id, 
            @RequestHeader(name = "token", required = false) String token,
            @RequestParam(name = "offset", required = true) int offset,
            @RequestParam(name = "limit", required = true) int limit) {
        
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        
        boolean isRight = true;
        
        User user = null;
        if (token != null) {
            try {
                user = userService.getByAccessToken(token);
            } catch (DeadAccessTokenException ex) {
                isRight = false;
                apiResult.setCode(errorCodes.getInvalidOrOldAccessToken());
                Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UserNotFoundException ex) {
                isRight = false;
                apiResult.setCode(errorCodes.getNotFound());
                Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (isRight) {
            try {
                PlaceCommentsResult placeCommentsResult = placeService.getComments(id, offset, limit, user);
                apiResult.setBody(placeCommentsResult);
            } catch (PermissionDeniedException ex) {
                Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
                apiResult.setCode(errorCodes.getPermissionDenied());
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
                apiResult.setCode(errorCodes.getInvalidRequest());
            } catch (PlaceNotFoundException ex) {
                Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
                apiResult.setCode(errorCodes.getNotFound());
            }
        }
        
        return apiResult;
    }
    
    @RequestMapping(value = "/places/{id}", method = RequestMethod.GET)
    public ApiResult placeById(@PathVariable("id") long id, 
            @RequestHeader(name = "token", required = false) String token) {
        
        ApiResult apiResult = new ApiResult(errorCodes.getSuccess());
        
        boolean isRight = true;
        
        User user = null;
        if (token != null) {
            try {
                user = userService.getByAccessToken(token);
            } catch (DeadAccessTokenException ex) {
                isRight = false;
                apiResult.setCode(errorCodes.getInvalidOrOldAccessToken());
                Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UserNotFoundException ex) {
                isRight = false;
                apiResult.setCode(errorCodes.getNotFound());
                Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (isRight) {
            try {
                PlaceResult placeResult = placeService.getPlace(id, user);
                apiResult.setBody(placeResult);
            } catch (PermissionDeniedException ex) {
                apiResult.setCode(errorCodes.getPermissionDenied());
                Logger.getLogger(PlaceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return apiResult;
    }
}
