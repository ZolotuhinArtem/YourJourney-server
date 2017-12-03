/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.config.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author rtmss
 */
@Component
@PropertySource({"classpath:/app.properties"})
public class StorageProviderImpl implements StorageProvider{

    @Autowired
    private Environment env;
    
    @Override
    public String getStoragePath() {
        return env.getProperty("storage");
    }
    
}
