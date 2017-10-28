/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

/**
 *
 * @author rtmss
 */
public class UserResult {
    
    private Long id;
    
    private String name;
    
    private String about;
    
    @JsonProperty(value = "home_country")
    private String homeCountry;
    
    @JsonProperty(value = "home_city")
    private String homeCity;
    
    @JsonProperty(value = "place_created")
    private int placeCreated;
    
    private Set<Long> places;

    public UserResult(Long id, String name, String about, String homeCountry, String homeCity, int placeCreated, Set<Long> places) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.homeCountry = homeCountry;
        this.homeCity = homeCity;
        this.placeCreated = placeCreated;
        this.places = places;
    }

    public UserResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public int getPlaceCreated() {
        return placeCreated;
    }

    public void setPlaceCreated(int placeCreated) {
        this.placeCreated = placeCreated;
    }

    public Set<Long> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Long> places) {
        this.places = places;
    }
    
    
    
    
}
