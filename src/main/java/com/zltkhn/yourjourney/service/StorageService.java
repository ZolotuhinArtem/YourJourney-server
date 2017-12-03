/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service;

import com.zltkhn.yourjourney.service.api.exception.NotFoundException;
import com.zltkhn.yourjourney.service.exception.EmptyFileException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rtmss
 */
public interface StorageService {
    
    /**
     * 
     * @param multipartFile
     * @param fileName - it is not path, it is just file name like avatar.png, music.mp3 and other
     * @throws EmptyFileException
     */
    void save(MultipartFile multipartFile, String fileName) throws EmptyFileException;
    
    byte[] get(String index) throws NotFoundException;
    
    void remove(String name);
    
}
