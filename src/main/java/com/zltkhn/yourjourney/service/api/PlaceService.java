/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api;

import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.service.api.exception.PermissionDeniedException;
import com.zltkhn.yourjourney.service.api.response.PlaceResult;
import com.zltkhn.yourjourney.service.api.response.PlacesShortResult;

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
    
}
