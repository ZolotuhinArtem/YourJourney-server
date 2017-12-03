/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api;

import com.zltkhn.yourjourney.service.api.exception.InvalidFormException;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.entities.UserToken;
import com.zltkhn.yourjourney.form.EditProfileForm;
import com.zltkhn.yourjourney.form.validator.EditProfileFormValidator;
import com.zltkhn.yourjourney.repository.UserRepository;
import com.zltkhn.yourjourney.repository.UserTokenRepository;
import com.zltkhn.yourjourney.service.Crypter;
import com.zltkhn.yourjourney.service.api.auth.AuthService;
import com.zltkhn.yourjourney.service.api.exception.DeadAccessTokenException;
import com.zltkhn.yourjourney.service.api.exception.UserNotFoundException;
import com.zltkhn.yourjourney.service.api.response.ProfileResult;
import com.zltkhn.yourjourney.service.api.converter.UserToProfileResultConverter;
import com.zltkhn.yourjourney.service.api.exception.ContentTypeOfMultipartFileIsNotImageException;
import com.zltkhn.yourjourney.service.exception.EmptyFileException;
import com.zltkhn.yourjourney.tools.ContentTypeRecognizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.zltkhn.yourjourney.service.StorageService;

/**
 *
 * @author rtmss
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserTokenRepository userTokenRepository;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserToProfileResultConverter userToProfileResultConverter;
    
    @Autowired
    private EditProfileFormValidator editProfileFormValidator;
    
    @Autowired
    private Crypter crypter;
    
    @Autowired
    private StorageService storageService;
    
    @Autowired
    private ContentTypeRecognizer contentTypeRecognizer;
    
    
    @Override
    public User getById(Long id) throws UserNotFoundException {
        if (id == null) {
            throw new UserNotFoundException();
        }
        User user = userRepository.findOne(id);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User getByAccessToken(String accessToken) throws DeadAccessTokenException, UserNotFoundException {
        if (accessToken == null) {
            throw  new DeadAccessTokenException();
        }
        
        UserToken token = userTokenRepository.findOneByAccessToken(accessToken);
        if (token != null) {
            if (token.getExpiresIn() > System.currentTimeMillis()) {
                User user = token.getUser();
                if (user != null) {
                    return user;
                } else {
                    throw new UserNotFoundException();
                }
            } else {
                throw new DeadAccessTokenException();
            }
        } else {
            throw new DeadAccessTokenException();
        }
    }

    @Override
    public ProfileResult getPojoById(Long id, boolean showPlaces) throws UserNotFoundException {
        
        User user = userRepository.findOne(id);
        
        if (user != null) {
            ProfileResult profileResult = userToProfileResultConverter.convert(user, showPlaces, false, true);
            
            if (profileResult != null) {
                return profileResult;
            } else {
                throw new UserNotFoundException(ProfileResult.class.getName() + " is null");
            }
        } else {
            throw new UserNotFoundException();
        }
        
    }

    @Override
    public ProfileResult getPojoByAccessToken(String accessToken) throws DeadAccessTokenException, UserNotFoundException {
        if (authService.isAccessTokenActive(accessToken)) {
            UserToken userToken = userTokenRepository.findOneByAccessToken(accessToken);
            if (userToken != null) {
                
                User user = userToken.getUser();
                
                if (user != null) {
                    
                    ProfileResult profileResult = userToProfileResultConverter.convert(user, true, true, false);
                    
                    if (profileResult != null) {
                        
                        return profileResult;
                        
                    } else {
                        throw new UserNotFoundException();
                    }
                    
                } else {
                    throw new UserNotFoundException();
                }
                
            } else {
                throw new DeadAccessTokenException();
            }
        } else {
            throw new DeadAccessTokenException();
        }
        
    }

    @Override
    public void editUser(String accessToken, EditProfileForm editProfileForm) 
            throws DeadAccessTokenException, UserNotFoundException, InvalidFormException {
        if (editProfileFormValidator.validate(editProfileForm)) {
            if (authService.isAccessTokenActive(accessToken)) {
                User user = authService.getUserByAccessToken(accessToken);
                
                if (editProfileForm.getPasswordOld() != null) {
                    if (!crypter.crypt(editProfileForm.getPasswordOld())
                            .equals(user.getPasswordCrypt())) {
                        throw new InvalidFormException();
                    }
                }
               
                updateUserFromEditeProfileForm(user, editProfileForm);
                
            } else {
                new DeadAccessTokenException();
            }
        } else {
            throw new InvalidFormException();
        }
    }
    
    private void updateUserFromEditeProfileForm(User user, EditProfileForm epf) {
        if (epf.getName() != null) {
            user.setName(epf.getName());
        }
        
        if (epf.getEmail() != null) {
            user.setEmail(epf.getEmail());
        }
        
        if (epf.getAbout() != null) {
            user.setAbout(epf.getAbout());
        }
        
        if (epf.getGender() != null) {
            user.setGender(epf.getGender());
        }
        
        if (epf.getHomeCountry() != null) {
            user.setCountry(epf.getHomeCountry());
        }
        
        if (epf.getHomeCity() != null) {
            user.setCity(epf.getHomeCity());
        }
        if (epf.getPasswordNew() != null) {
            user.setPasswordCrypt(crypter.crypt(epf.getPasswordNew()));
        }
        
        userRepository.save(user);
        
    }

    @Override
    public String updateAvatar(User user, MultipartFile multipartFile) throws IllegalArgumentException,
            ContentTypeOfMultipartFileIsNotImageException{
        
        if (user == null) {
            throw new IllegalArgumentException("User must be not null");
        }
        
        
        
        String oldPhotoName = user.getAvatar();
        
        
        if (multipartFile != null) {
            
            if (contentTypeRecognizer.recognize(multipartFile.getContentType()) == ContentTypeRecognizer.TYPE_IMAGE){
                
                String fileName = generateAvatarName(user, multipartFile);                
                try {
                    storageService.save(multipartFile, fileName);
                    
                    user.setAvatar(fileName);
                    
                    userRepository.save(user);
                    
                    storageService.remove(oldPhotoName);
                    
                    return fileName;
                    
                } catch (EmptyFileException ex) {
                    Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    throw new IllegalArgumentException("file is empty");
                }
                    
            } else {
                throw new ContentTypeOfMultipartFileIsNotImageException();
            }
        
        } else {
            user.setAvatar("");
            userRepository.save(user);
            storageService.remove(oldPhotoName);
        }
        return null;
    }
    
    /**
     * 
     * @param user must be not null
     * @param multipartFile must be not null
     * @return 
     */
    private String generateAvatarName(User user, MultipartFile multipartFile) {
        
        String name = crypter.crypt(System.currentTimeMillis() + "")
                + crypter.crypt(System.nanoTime() + "" + user.getId());
        
        String ext = "";
        
        if (multipartFile.getContentType().toLowerCase().contains("jpeg")) {
            ext = ".jpg";
        } else {
            if (multipartFile.getContentType().toLowerCase().contains("png")) {
                ext = ".png";
            }
        }
        
        return name + ext;
    }
    
}
