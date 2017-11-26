/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.repository;

import com.zltkhn.yourjourney.entities.Place;
import com.zltkhn.yourjourney.entities.PlaceLike;
import com.zltkhn.yourjourney.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rtmss
 */
@Repository
public interface PlaceLikeRepository extends JpaRepository<PlaceLike, Long>{
    
    @Query("SELECT pl FROM PlaceLike pl WHERE pl.place = ?1 AND pl.user = ?2")
    PlaceLike findByPlaceAndUser(Place place, User user);
    
}
