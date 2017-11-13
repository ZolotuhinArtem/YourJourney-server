/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.converter;

import com.zltkhn.yourjourney.entities.Comment;
import com.zltkhn.yourjourney.entities.Place;
import com.zltkhn.yourjourney.entities.PlaceLike;
import com.zltkhn.yourjourney.entities.PlacePhoto;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.service.api.response.PlaceResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author rtmss
 */
@Component
public class PlaceToPlaceResultConverterImpl implements PlaceToPlaceResultConverter{

    @Override
    public PlaceResult convert(Place place, User user) {
        
        if (place == null) {
            return null;
        }
        PlaceResult placeResult = new PlaceResult();
        
        placeResult.setId(place.getId());
        
        placeResult.setTitle(place.getTitle());
        
        placeResult.setLat(place.getLat());
        
        placeResult.setLon(place.getLon());
        
        Set<PlaceLike> likes = place.getLikes();
        placeResult.setLikes(likes != null ? likes.size() : 0L);
        
        Set<Comment> comments = place.getComments();
        placeResult.setComments(comments != null ? comments.size() : 0L);
        
        placeResult.setDescription(place.getDescription());
        
        placeResult.setIsPrivate(place.getIsPrivate());
        
        User owner = place.getUser();
        if (owner != null) {
            placeResult.setOwnerId(owner.getId());
        }
        
        Set<PlacePhoto> photosSet = place.getPhotos();
        if (photosSet != null) {
            
            List<PlacePhoto> photosList = new ArrayList<PlacePhoto>(photosSet.size());
            
            photosList.addAll(photosSet);
            
            photosList.sort((PlacePhoto p1, PlacePhoto p2) -> {
                if (p1 == null) {
                    return 1;
                }
                if (p2 == null) {
                    return -1;
                }
                
                if (p1.getIdx() > p2.getIdx()) {
                    return 1;
                } else {
                    if (p1.getIdx() < p2.getIdx()) {
                        return -1;
                    }
                }
                
                return 0;
            });
            
            List<String> photoUriList = new ArrayList<String>();
            
            for (PlacePhoto placePhoto : photosList) {
                if (placePhoto != null) {
                    photoUriList.add(placePhoto.getUri());
                }
            }
            
            placeResult.setPhotos(photoUriList);
        }
        
        placeResult.setIsLiked(false);
        if (user != null) {
            User iUser;
            for (PlaceLike placeLike : likes) {
                if (placeLike != null) {
                    iUser = placeLike.getUser();
                    if (iUser != null) {
                        if (iUser.getId().equals(user.getId())) {
                            placeResult.setIsLiked(true);
                            break;
                        }
                    }
                }
            }
        }
        
        return placeResult;
    }
    
}
