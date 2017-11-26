/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.form.validator;

import com.zltkhn.yourjourney.form.CommentForm;
import com.zltkhn.yourjourney.tools.StringTool;

/**
 *
 * @author rtmss
 */
public class CommentFormValidatorImpl implements CommentFormValidator{

    
    private int minTextLength;
    private int maxTextLength;

    public CommentFormValidatorImpl(int minTextLength, int maxTextLength) {
        this.minTextLength = minTextLength;
        this.maxTextLength = maxTextLength;
    }
    
            
    @Override
    public boolean validate(CommentForm form) {
        if (form == null) {
            return false;
        } else {
            return StringTool.isInBounds(form.getText(), minTextLength, maxTextLength);
        }
    }

    public int getMinTextLength() {
        return minTextLength;
    }

    public void setMinTextLength(int minTextLength) {
        this.minTextLength = minTextLength;
    }

    public int getMaxTextLength() {
        return maxTextLength;
    }

    public void setMaxTextLength(int maxTextLength) {
        this.maxTextLength = maxTextLength;
    }
    
    
    
}
