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
public class UserAlreadyReportedException extends Exception{

    public UserAlreadyReportedException() {
    }

    public UserAlreadyReportedException(String message) {
        super(message);
    }

    public UserAlreadyReportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyReportedException(Throwable cause) {
        super(cause);
    }
    
}
