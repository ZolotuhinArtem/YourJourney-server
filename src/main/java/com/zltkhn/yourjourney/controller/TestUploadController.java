/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.controller;

import com.zltkhn.yourjourney.service.MultipartFileService;
import com.zltkhn.yourjourney.service.exception.EmptyFileException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rtmss
 */
@Controller
public class TestUploadController {
   
    
    @Autowired
    private MultipartFileService multipartFileService;
    
    @RequestMapping(value = "/test_upload") 
    public String uploadGet() {
        return "test_upload";
        
    }
     
    @RequestMapping(value = "/test_upload", method = RequestMethod.POST) 
    public String uploadPost(@RequestParam("image") MultipartFile image, ModelMap map) {
        
        try {
            String index = multipartFileService.save(image);
            map.put("message", index);
        } catch (EmptyFileException ex) {
            Logger.getLogger(TestUploadController.class.getName()).log(Level.SEVERE, null, ex);
            map.put("message", "Sad :(");
        }
        
        return "test_upload";
        
    }
    
}
