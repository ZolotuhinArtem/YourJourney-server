/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.form;

import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.service.Crypter;
/**
 *
 * @author rtmss
 */
public class RegistrationForm {
    
    private String name;
    
    private String email;
    
    private String password;

    public RegistrationForm() {
    }

    public RegistrationForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public User generateUser(Crypter crypter) {
        User user = new User();
        user.setEmail(email);
        user.setPasswordCrypt(crypter.crypt(password));
        user.setName(name);
        return user;
    }

    @Override
    public String toString() {
        return "RegistrationForm{" + "name=" + name + ", email=" + email + ", password=" + password + '}';
    }
    
    
    
}
