/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.form.validator;

import com.zltkhn.yourjourney.form.LoginForm;
import org.springframework.stereotype.Service;

/**
 *
 * @author rtmss
 */
@Service
public class LoginFormValidatorImpl implements LoginFormValidator{

    @Override
    public int validate(LoginForm loginForm) {
        if (loginForm == null) {
            return ERROR_LOGIN_OR_PASSWORD_INCORRECT;
        }
        return CODE_OK;
    }
    
    
    
}
