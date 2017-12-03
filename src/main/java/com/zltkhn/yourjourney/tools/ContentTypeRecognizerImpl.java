/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.tools;

import org.springframework.stereotype.Component;

/**
 *
 * @author rtmss
 */
@Component
public class ContentTypeRecognizerImpl implements ContentTypeRecognizer{

    @Override
    public int recognize(String contentType) {
        if (contentType == null) {
            return TYPE_UNKNOWN;
        }
        
        if (contentType.toLowerCase().equals("image/jpeg") || contentType.toLowerCase().equals("image/png")) {
            return TYPE_IMAGE;
        }
        
        return TYPE_UNKNOWN;
    }
    
}
