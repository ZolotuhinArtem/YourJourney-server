/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.auth;

/**
 *
 * @author rtmss
 */
public class DeadAccessTokenException extends Exception {

    public DeadAccessTokenException() {
    }

    public DeadAccessTokenException(String message) {
        super(message);
    }

    public DeadAccessTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeadAccessTokenException(Throwable cause) {
        super(cause);
    }
    
}
