/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.response;

import java.util.Set;

/**
 *
 * @author rtmss
 */
public class PlacesResult {
    
    private Set<PlaceResult> places;

    public Set<PlaceResult> getPlaces() {
        return places;
    }

    public void setPlaces(Set<PlaceResult> places) {
        this.places = places;
    }
    
    
}
