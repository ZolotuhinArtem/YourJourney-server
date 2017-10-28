/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.form.validator;

import com.zltkhn.yourjourney.form.LoginForm;

/**
 *
 * @author rtmss
 */
public interface LoginFormValidator {
 
    int CODE_OK = 0;
    
    int ERROR_LOGIN_OR_PASSWORD_INCORRECT = 1;
    
    int validate(LoginForm loginForm);
    
}
