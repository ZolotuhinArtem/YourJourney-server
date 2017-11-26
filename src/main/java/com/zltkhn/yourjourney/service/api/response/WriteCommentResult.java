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
public class WriteCommentResult {
 
    
    private Long id;

    public WriteCommentResult() {
    }

    public WriteCommentResult(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
