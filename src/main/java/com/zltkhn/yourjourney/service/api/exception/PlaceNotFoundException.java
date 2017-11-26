/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.exception;

/**
 *
 * @author rtmss
 */
public class PlaceNotFoundException extends Exception{

    public PlaceNotFoundException() {
    }

    public PlaceNotFoundException(String message) {
        super(message);
    }

    public PlaceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlaceNotFoundException(Throwable cause) {
        super(cause);
    }
 
    
    
}
