/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service;

import com.zltkhn.yourjourney.service.exception.EmptyFileException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rtmss
 */
@Service
public class MultipartFileServiceImpl implements MultipartFileService{

    @Override
    public String save(MultipartFile multipartFile) throws EmptyFileException{
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException();
        }
        
        String filename = generateFilename();
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(MultipartFileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("SEE FUCKING HERE: " + file.getAbsolutePath());
            return filename;
        } catch (IOException ex) {
            Logger.getLogger(MultipartFileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            if (file.exists()) {
                file.delete();
            }
            return null;
        }        
    }

    @Override
    public File get(String index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String generateFilename() {
        return Long.toString(System.currentTimeMillis()) + (new Random(System.currentTimeMillis()).nextLong());
    }
    
}
