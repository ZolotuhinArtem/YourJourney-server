/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.response;

/**
 *
 * @author rtmss
 */
public class EditAvatarResult {
 
    private String url;

    public EditAvatarResult(String url) {
        this.url = url;
    }
    
    

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
}
