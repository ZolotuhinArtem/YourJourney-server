/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
}
