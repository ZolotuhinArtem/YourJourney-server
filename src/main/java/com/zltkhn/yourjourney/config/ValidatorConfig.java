/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.config;

import com.zltkhn.yourjourney.form.validator.RegistrationFormValidator;
import com.zltkhn.yourjourney.form.validator.RegistrationFormValidatorImpl;
import com.zltkhn.yourjourney.tools.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author rtmss
 */
@Configuration
@ComponentScan(basePackages = {"com.zltkhn.yourjourney.form.validator"})
public class ValidatorConfig {
    
    @Autowired
    @Bean
    public RegistrationFormValidator registrationFormValidator(EmailValidator emailValidator) {
        return new RegistrationFormValidatorImpl(1, 128, 5, 256, 8, 256, emailValidator);
    }
}
