/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.form.validator;

import com.zltkhn.yourjourney.form.RegistrationForm;
import com.zltkhn.yourjourney.tools.StringTool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

/**
 *
 * @author rtmss
 */
public class RegistrationFormValidatorImpl implements RegistrationFormValidator{

    
    private int minNameLength;
    
    private int maxNameLength;

    private int minEmailLength;
    
    private int maxEmailLength;
    
    private int minPasswordLength;
    
    private int maxPasswordLength;

    public RegistrationFormValidatorImpl(int minNameLength, int maxNameLength, int minEmailLength, int maxEmailLength, int minPasswordLength, int maxPasswordLength) {
        this.minNameLength = minNameLength;
        this.maxNameLength = maxNameLength;
        this.minEmailLength = minEmailLength;
        this.maxEmailLength = maxEmailLength;
        this.minPasswordLength = minPasswordLength;
        this.maxPasswordLength = maxPasswordLength;
    }

    @Override
    public boolean validate(RegistrationForm registrationForm) {
        if (registrationForm == null) {
            return false;
        } else {
            return checkEmail(registrationForm.getEmail()) &&
                    checkName(registrationForm.getName()) &&
                    checkPassword(registrationForm.getPassword());
        }
    }
    
    private boolean checkEmail(String email) {
        if (email == null) {
            return false;
        } else {
           if (!StringTool.isInBounds(email, minEmailLength, maxEmailLength)) {
               return false;
           }
        }

        Pattern p = Pattern.compile(".+@.+\\..+");
        Matcher m = p.matcher(email);

        return m.matches();
    }
    
    private boolean checkName(String name){
        return StringTool.isInBounds(name, minNameLength, maxNameLength);
    }

    private boolean checkPassword(String password){
        return StringTool.isInBounds(password, minPasswordLength, maxPasswordLength);
    }
    
    
}
