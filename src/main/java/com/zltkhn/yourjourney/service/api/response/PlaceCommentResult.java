/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author rtmss
 */
public class PlaceCommentResult {
    
    private Long id;
    
    private Long date;
    
    @JsonProperty(value = "owner_id")
    private Long ownerId;
    
    private String text;

    public PlaceCommentResult() {
    }

    public PlaceCommentResult(Long id, Long date, Long ownerId, String text) {
        this.id = id;
        this.date = date;
        this.ownerId = ownerId;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
    
    
}
