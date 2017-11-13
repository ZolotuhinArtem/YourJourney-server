/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.converter;

import com.zltkhn.yourjourney.entities.Place;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.service.api.response.PlaceResult;
import com.zltkhn.yourjourney.service.api.response.PlaceShortResult;

/**
 *
 * @author rtmss
 */
public interface PlaceToPlaceResultConverter {
    
    
    /**
     * 
     * @param place
     * @param user can be null
     * @return 
     */
    PlaceResult convert(Place place, User user);
}
