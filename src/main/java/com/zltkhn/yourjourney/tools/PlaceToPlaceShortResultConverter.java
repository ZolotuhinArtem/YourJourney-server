/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.tools;

import com.zltkhn.yourjourney.entities.Place;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.service.api.response.PlaceShortResult;

/**
 *
 * @author rtmss
 */
public interface PlaceToPlaceShortResultConverter {
    
    PlaceShortResult convert(User user, Place place);
    
}
