/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.tools;

/**
 *
 * @author rtmss
 */
public interface ContentTypeRecognizer {
    
    static final int TYPE_UNKNOWN = 0;
    
    static final int TYPE_IMAGE = 1;
    
    int recognize(String contentType);
    
}
