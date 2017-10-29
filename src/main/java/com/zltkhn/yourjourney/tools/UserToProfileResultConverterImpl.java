/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.tools;

import com.zltkhn.yourjourney.entities.Place;
import com.zltkhn.yourjourney.entities.PlaceLike;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.repository.PlaceRepository;
import com.zltkhn.yourjourney.service.api.response.ProfileResult;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author rtmss
 */
@Component
public class UserToProfileResultConverterImpl implements UserToProfileResultConverter{

    @Autowired
    private PlaceRepository placeRepository;
    
    @Override
    public ProfileResult convert(User user) {
        if (user != null) {
            ProfileResult profileResult = new ProfileResult();
            profileResult.setId(user.getId());
            profileResult.setEmail(user.getEmail());
            profileResult.setAbout(user.getAbout());
            profileResult.setHomeCity(user.getCity());
            profileResult.setHomeCountry(user.getCountry());
            profileResult.setName(user.getName());
            profileResult.setPlaces(placeRepository.findPlacesIdByUserId(user));
            
            Set<User> friends = user.getFriends();
            Set<Long> frienIds = new HashSet<>();
            if (friends != null) {
                for (User u : friends) {
                    if (u != null) {
                        frienIds.add(u.getId());
                    }
                }
            }
            profileResult.setFriends(frienIds);
            
            Set<PlaceLike> placeLikes = user.getPlaceLikes();
            Set<Long> likedPlaces = new HashSet<>();
            Place place;
            if (placeLikes != null) {
                for (PlaceLike p : placeLikes) {
                    if (p != null) {
                        place = p.getPlace();
                        if (place != null) {
                            likedPlaces.add(place.getId());
                        }
                    }
                }
            }
            profileResult.setLikedPlaces(likedPlaces);
            
            return profileResult;
            
        } else {
            return null;
        }
    }
    
}
