/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.converter;

import com.zltkhn.yourjourney.entities.PlaceComment;
import com.zltkhn.yourjourney.service.api.response.PlaceCommentResult;

/**
 *
 * @author rtmss
 */
public interface PlaceCommentToPlaceCommentResultConverter {
    
    PlaceCommentResult convert(PlaceComment placeComment);
    
}
