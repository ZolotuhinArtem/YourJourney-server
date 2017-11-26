/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.repository;

import com.zltkhn.yourjourney.entities.Place;
import com.zltkhn.yourjourney.entities.PlaceReport;
import com.zltkhn.yourjourney.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rtmss
 */
@Repository
public interface PlaceReportRepository extends JpaRepository<PlaceReport, Long> {
    
    @Query("SELECT pr FROM PlaceReport pr WHERE pr.place = ?1 AND pr.user = ?2")
    PlaceReport findByPlaceAndUser(Place place, User user);
    
}
