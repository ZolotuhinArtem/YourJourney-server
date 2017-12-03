/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service;

import com.zltkhn.yourjourney.service.api.exception.NotFoundException;
import com.zltkhn.yourjourney.service.exception.EmptyFileException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rtmss
 */
@Service
@PropertySource({"classpath:/app.properties"})
public class StorageServiceImpl implements StorageService{

    @Autowired
    private Environment env;
    
    @Override
    public void save(MultipartFile multipartFile, String fileName) throws EmptyFileException{
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException();
        }
        
        String path = getStoragePath() + "/" + fileName;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(StorageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try (InputStream is = multipartFile.getInputStream();
            BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(file));) {
            int b;
            while ((b = is.read()) >= 0) {
                stream.write(b);
            }
            stream.flush();
        } catch (IOException ex) {
            Logger.getLogger(StorageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            if (file.exists()) {
                file.delete();
            }
        }        
    }

    @Override
    public byte[] get(String index) throws NotFoundException{
        String path = getStoragePath() + "/" + index;
        File file = new File(path);
        if (file.exists()) {
            try {
                byte[] raw = Files.readAllBytes(Paths.get(path));
                return raw;
            } catch (IOException ex) {
                Logger.getLogger(StorageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new NotFoundException(ex);
            }
        } else {
            throw new NotFoundException("Not found at " + path);
        }
    }
    
    public String getStoragePath() {
        return env.getProperty("storage");
    }

    @Override
    public void remove(String name) {
        String path = getStoragePath() + "/" + name;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
    
}
