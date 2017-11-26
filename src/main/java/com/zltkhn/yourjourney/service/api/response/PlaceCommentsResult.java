/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zltkhn.yourjourney.entities.PlaceComment;
import java.util.List;
import java.util.Set;

/**
 *
 * @author rtmss
 */
public class PlaceCommentsResult {
    
    @JsonProperty(value = "count_all")
    private Long countAll;
    
    private List<PlaceCommentResult> comments;

    public Long getCountAll() {
        return countAll;
    }

    public void setCountAll(Long countAll) {
        this.countAll = countAll;
    }

    public List<PlaceCommentResult> getComments() {
        return comments;
    }

    public void setComments(List<PlaceCommentResult> comments) {
        this.comments = comments;
    }
    
    
    
}
