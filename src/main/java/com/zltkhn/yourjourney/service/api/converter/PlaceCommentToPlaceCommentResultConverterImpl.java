/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.converter;

import com.zltkhn.yourjourney.entities.PlaceComment;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.service.api.response.PlaceCommentResult;
import org.springframework.stereotype.Component;

/**
 *
 * @author rtmss
 */
@Component
public class PlaceCommentToPlaceCommentResultConverterImpl implements 
        PlaceCommentToPlaceCommentResultConverter{

    @Override
    public PlaceCommentResult convert(PlaceComment placeComment) {
        if (placeComment == null) {
            return null;
        }
        
        PlaceCommentResult placeCommentResult = new PlaceCommentResult();
        
        placeCommentResult.setId(placeComment.getId());
        placeCommentResult.setDate(placeComment.getCommentDate());
        placeCommentResult.setText(placeComment.getBody());
        
        User user = placeComment.getUser();
        if (user != null) {
            placeCommentResult.setOwnerId(user.getId());
        }
        
        return placeCommentResult;
    }
    
}
