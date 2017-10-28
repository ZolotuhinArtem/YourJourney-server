/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api;

/**
 *
 * @author rtmss
 */
public interface ErrorCodes {
 
    int getSuccess();
    
    int getInvalidOrOldAccessToken();
    
    int getPermissionDenoed();
    
    int getNotFound();
    
    int getInvalidRequest();
    
    int getEmptyText();
    
    int getBigText();
    
    int getBigPhoto();
    
    int getInvalidLoginOrPassword();
    
    int getInvalidLogin();
    
    int getLongPassword();
    
    int getShortPassword();
    
    int getUserWithSameLoginAlreadyExists();
    
    int getShortName();
    
    int getLongName();
    
    int getLongAbout();
    
    int getInvalidPassword();
    
    int getShortTitle();
    
    int getLongTitle();
    
    int getLongDescription();
    
    int getBigCountOfPhotos();
    
    int getYouAlreadyReportIt();
    
    int getInvalidForm();
    
}
