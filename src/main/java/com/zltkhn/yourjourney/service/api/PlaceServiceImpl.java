/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api;

import com.zltkhn.yourjourney.entities.Place;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.repository.PlaceRepository;
import com.zltkhn.yourjourney.service.api.converter.PlaceToPlaceResultConverter;
import com.zltkhn.yourjourney.service.api.exception.PermissionDeniedException;
import com.zltkhn.yourjourney.service.api.response.PlaceResult;
import com.zltkhn.yourjourney.service.api.response.PlaceShortResult;
import com.zltkhn.yourjourney.service.api.response.PlacesShortResult;
import com.zltkhn.yourjourney.service.api.converter.PlaceToPlaceShortResultConverter;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rtmss
 */
@Service
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    private PlaceRepository placeRepository;
    
    @Autowired
    private PlaceToPlaceShortResultConverter placeShortResultConverter;
    
    @Autowired
    private PlaceToPlaceResultConverter placeResultConverter;
    
    @Override
    public PlacesShortResult getShortInfoPlacesByLocation(String country, String city, User userWhoBrowses) throws IllegalArgumentException{
        
        if (country == null || city == null) {
            throw new IllegalArgumentException("Country and city must be not null." +
                    "country = \"" + country + "\"; city = \"" + city + "\"");
        }
        
        Set<Place> places = placeRepository.findPlacesByCountryAndCityAndPrivate(country, city, false);
        
        Set<PlaceShortResult> placeShortResults = new HashSet<>();
        
        PlaceShortResult placeShortResult;
        
        if (places != null) {
            for (Place place : places) {
                if (place != null)  {
                    if (!place.getIsPrivate()) {
                        placeShortResult = placeShortResultConverter.convert(userWhoBrowses, place);
                        if (placeShortResult != null) {
                            placeShortResults.add(placeShortResult);
                        }
                    }
                }
            }
        }
        
        PlacesShortResult placesResult = new PlacesShortResult();
        placesResult.setPlaces(placeShortResults);
        
        return placesResult;
    }

    @Override
    public PlaceResult getPlace(long id, User userWhoBrowses) throws PermissionDeniedException {
        Place place = placeRepository.findOne(id);
        if (place != null) {
            
            if (place.getIsPrivate()) {
                if (userWhoBrowses != null) {
                    User owner = place.getUser();
                    if (owner == null) {
                        throw new PermissionDeniedException();
                    }
                    if (owner.getId().equals(userWhoBrowses.getId())) {
                        throw new PermissionDeniedException(); 
                    }
                    
                }
            }
            
            PlaceResult placeResult = placeResultConverter.convert(place, userWhoBrowses);
            
            return placeResult;
            
        } else {
            return null;
        }
        
    }
    
}
