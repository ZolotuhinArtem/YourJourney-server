/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service;

import com.zltkhn.yourjourney.service.exception.EmptyFileException;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rtmss
 */
public interface MultipartFileService {
    
    /**
     * 
     * @param multipartFile
     * @return index
     */
    String save(MultipartFile multipartFile, String path) throws EmptyFileException;
    
    File get(String index);
    
}
