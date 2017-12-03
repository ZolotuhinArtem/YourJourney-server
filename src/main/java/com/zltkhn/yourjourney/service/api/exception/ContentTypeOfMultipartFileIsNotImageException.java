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
public class ContentTypeOfMultipartFileIsNotImageException extends Exception{

    public ContentTypeOfMultipartFileIsNotImageException() {
    }

    public ContentTypeOfMultipartFileIsNotImageException(String message) {
        super(message);
    }

    public ContentTypeOfMultipartFileIsNotImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentTypeOfMultipartFileIsNotImageException(Throwable cause) {
        super(cause);
    }
    
}
