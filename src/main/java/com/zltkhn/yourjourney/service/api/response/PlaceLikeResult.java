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
public class PlaceLikeResult {
    
    private boolean liked;

    public PlaceLikeResult() {
    }

    public PlaceLikeResult(boolean liked) {
        this.liked = liked;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
