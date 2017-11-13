/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.repository;

import com.zltkhn.yourjourney.entities.Place;
import com.zltkhn.yourjourney.entities.User;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rtmss
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>{
    
    @Query("SELECT p.id FROM Place p WHERE p.user = ?1")
    Set<Long> findPlacesIdByUserId(User user);
    
    @Query("SELECT p.id FROM Place p WHERE p.user = ?1 AND p.isPrivate = ?2")
    Set<Long> findPlacesIdByUserIdAndPrivate(User user, Boolean isPrivate);
    
    @Query("SELECT p FROM Place p WHERE LOWER(p.country) = LOWER(?1) AND LOWER(p.city) = LOWER(?2) AND p.isPrivate = ?3")
    Set<Place> findPlacesByCountryAndCityAndPrivate(String country, String city, Boolean isPrivate);
    
}
