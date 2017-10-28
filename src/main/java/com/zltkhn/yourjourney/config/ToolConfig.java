/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.config;

import com.zltkhn.yourjourney.service.Crypter;
import com.zltkhn.yourjourney.service.Md5Crypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author rtmss
 */
@Configuration
@PropertySource({"classpath:/app.properties"})
public class ToolConfig {
    
    @Autowired
    private Environment env;
    
    @Bean 
    public Crypter crypter() {
        return new Md5Crypter(this.env.getProperty("crypter.soil"));
    }
}
