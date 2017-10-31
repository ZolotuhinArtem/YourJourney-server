/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.tools;

import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.service.api.response.ProfileResult;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author rtmss
 */
public interface UserToProfileResultConverter {
    
    /**
     * 
     * @param user
     * @return null if user is null
     */
    ProfileResult convert(User user, boolean showPlaces,  boolean showLikedPlaces, boolean hidePrivatePlaces);
    
}
