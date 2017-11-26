/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api;

import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.form.CommentForm;
import com.zltkhn.yourjourney.service.api.exception.InvalidFormException;
import com.zltkhn.yourjourney.service.api.exception.NotFoundException;
import com.zltkhn.yourjourney.service.api.exception.PermissionDeniedException;
import com.zltkhn.yourjourney.service.api.exception.PlaceNotFoundException;
import com.zltkhn.yourjourney.service.api.response.PlaceCommentsResult;
import com.zltkhn.yourjourney.service.api.response.PlaceLikeResult;
import com.zltkhn.yourjourney.service.api.response.PlaceResult;
import com.zltkhn.yourjourney.service.api.response.PlacesShortResult;
import com.zltkhn.yourjourney.service.api.response.WriteCommentResult;

/**
 *
 * @author rtmss
 */
public interface PlaceService {
    
    
    /**
     * 
     * @param country
     * @param city
     * @param userWhoBrowses can be null
     * @return DOES NOT RETURN PRIVATE PLACES!
     */
    PlacesShortResult getShortInfoPlacesByLocation(String country, String city, User userWhoBrowses) throws IllegalArgumentException;
    
    /**
     * 
     * @param id
     * @param userWhoBrowses can be null
     * @return null if not exists
     */
    PlaceResult getPlace(long id, User userWhoBrowses) throws PermissionDeniedException;
    
    /**
     * 
     * @param id
     * @param offset
     * @param limit
     * @param userWhoBrowses can be null
     * @return
     * @throws PermissionDeniedException
     * @throws IllegalArgumentException
     * @throws PlaceNotFoundException 
     */
    PlaceCommentsResult getComments(long id, int offset, int limit, User userWhoBrowses) throws 
            PermissionDeniedException, IllegalArgumentException, PlaceNotFoundException;
    
    WriteCommentResult writeComment(long placeId, User user, CommentForm commentForm) throws
            PermissionDeniedException, PlaceNotFoundException, InvalidFormException;
    
    void removeComment(long commentId, User user) throws 
            PermissionDeniedException, NotFoundException, IllegalArgumentException;
    
    PlaceLikeResult like(long placeId, User user) throws
            PermissionDeniedException, NotFoundException, IllegalArgumentException;
    
}
