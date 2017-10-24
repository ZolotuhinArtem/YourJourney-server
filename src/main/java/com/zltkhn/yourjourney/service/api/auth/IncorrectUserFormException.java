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
public class IncorrectUserFormException extends Exception {

    
    public static final int ERROR_CODE_INCORRECT_LOGIN_OR_PASSWORD = 1;
    
    public static final int ERROR_CODE_NAME_LONG = 2;
    
    public static final int ERROR_CODE_NAME_SHORT = 4;
    
    public static final int ERROR_CODE_PASSWORD_LONG = 8;
    
    public static final int ERROR_CODE_PASSWORD_SHORT = 16;
    
    private int errorCode;
    
    public IncorrectUserFormException() {
    }

    public IncorrectUserFormException(String message) {
        super(message);
    }

    public IncorrectUserFormException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectUserFormException(Throwable cause) {
        super(cause);
    }

    /**
     * 
     * @return 0 if no errors, else sum of error codes (error codes is powers of 2)
     */
    public int getErrorCodeSum() {
        return errorCode;
    }

    public void setErrorCodeSum(int errorCode) {
        this.errorCode = errorCode;
    }
    
    
    
}
