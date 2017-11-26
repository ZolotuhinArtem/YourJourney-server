/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api;

import com.zltkhn.yourjourney.service.api.exception.InvalidFormException;
import com.zltkhn.yourjourney.entities.Place;
import com.zltkhn.yourjourney.entities.PlaceComment;
import com.zltkhn.yourjourney.entities.PlaceLike;
import com.zltkhn.yourjourney.entities.User;
import com.zltkhn.yourjourney.form.CommentForm;
import com.zltkhn.yourjourney.form.validator.CommentFormValidator;
import com.zltkhn.yourjourney.repository.PlaceCommentRepository;
import com.zltkhn.yourjourney.repository.PlaceLikeRepository;
import com.zltkhn.yourjourney.repository.PlaceRepository;
import com.zltkhn.yourjourney.service.api.comparator.PlaceCommentComparator;
import com.zltkhn.yourjourney.service.api.converter.PlaceCommentToPlaceCommentResultConverter;
import com.zltkhn.yourjourney.service.api.converter.PlaceToPlaceResultConverter;
import com.zltkhn.yourjourney.service.api.exception.PermissionDeniedException;
import com.zltkhn.yourjourney.service.api.response.PlaceResult;
import com.zltkhn.yourjourney.service.api.response.PlaceShortResult;
import com.zltkhn.yourjourney.service.api.response.PlacesShortResult;
import com.zltkhn.yourjourney.service.api.converter.PlaceToPlaceShortResultConverter;
import com.zltkhn.yourjourney.service.api.exception.NotFoundException;
import com.zltkhn.yourjourney.service.api.exception.PlaceNotFoundException;
import com.zltkhn.yourjourney.service.api.response.PlaceCommentResult;
import com.zltkhn.yourjourney.service.api.response.PlaceCommentsResult;
import com.zltkhn.yourjourney.service.api.response.PlaceLikeResult;
import com.zltkhn.yourjourney.service.api.response.WriteCommentResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rtmss
 */
@Service
public class PlaceServiceImpl implements PlaceService{

    @Autowired
    private PlaceRepository placeRepository;
    
    @Autowired
    private PlaceCommentRepository placeCommentRepository;
    
    @Autowired
    private PlaceLikeRepository placeLikeRepository;
    
    @Autowired
    private PlaceToPlaceShortResultConverter placeShortResultConverter;
    
    @Autowired
    private PlaceToPlaceResultConverter placeResultConverter;
    
    @Autowired
    private PlaceCommentComparator placeCommentComparator;
    
    @Autowired
    private PlaceCommentToPlaceCommentResultConverter placeCommentToPlaceCommentResultConverter;
    
    @Autowired
    private CommentFormValidator commentFormValidator;
    
    @Override
    public PlacesShortResult getShortInfoPlacesByLocation(String country, String city, User userWhoBrowses) throws IllegalArgumentException{
        
        if (country == null || city == null) {
            throw new IllegalArgumentException("Country and city must be not null." +
                    "country = \"" + country + "\"; city = \"" + city + "\"");
        }
        
        Set<Place> places = placeRepository.findPlacesByCountryAndCityAndPrivate(country, city, false);
        
        Set<PlaceShortResult> placeShortResults = new HashSet<>();
        
        PlaceShortResult placeShortResult;
        
        if (places != null) {
            for (Place place : places) {
                if (place != null)  {
                    if (!place.getIsPrivate()) {
                        placeShortResult = placeShortResultConverter.convert(userWhoBrowses, place);
                        if (placeShortResult != null) {
                            placeShortResults.add(placeShortResult);
                        }
                    }
                }
            }
        }
        
        PlacesShortResult placesResult = new PlacesShortResult();
        placesResult.setPlaces(placeShortResults);
        
        return placesResult;
    }

    @Override
    public PlaceResult getPlace(long id, User userWhoBrowses) throws PermissionDeniedException {
        Place place = placeRepository.findOne(id);
        if (place != null) {
            
            if (!accessGranted(place, userWhoBrowses)) {
                throw new PermissionDeniedException();
            }
            
            PlaceResult placeResult = placeResultConverter.convert(place, userWhoBrowses);
            
            return placeResult;
            
        } else {
            return null;
        }
        
    }

    @Override
    public PlaceCommentsResult getComments(long id, int offset, int limit, User userWhoBrowses) 
            throws PermissionDeniedException, IllegalArgumentException, PlaceNotFoundException {
        
        Place place = placeRepository.findOne(id);
        if (place == null) {
            throw new PlaceNotFoundException();
        }
        
        if (offset < 0 || limit < 0) {
            throw new IllegalArgumentException();
        }
        
        if (accessGranted(place, userWhoBrowses)) {
            
            Set<PlaceComment> placeCommentsSet = placeRepository.findComments(place);
            
            List<PlaceCommentResult> placeCommentResults = new ArrayList<>(limit);
            
            PlaceCommentsResult placeCommentsResult = new PlaceCommentsResult();
            placeCommentsResult.setComments(placeCommentResults);
            
            
            if (placeCommentsSet.size() > offset) {
                List<PlaceComment> placeComments = new ArrayList<>(placeCommentsSet.size());
                placeComments.addAll(placeCommentsSet);
                placeComments.sort(placeCommentComparator);
                
                for (int i = offset; i < Math.min(placeComments.size(), offset + limit); i++) {
                    placeCommentResults.add(placeCommentToPlaceCommentResultConverter.convert(placeComments.get(i)));
                }
            }
           
            
            return placeCommentsResult;
            
        } else {
            throw new PermissionDeniedException();
        }
    }
    
    /**
     * 
     * @param place
     * @param user can be null
     * @return 
     */
    private boolean accessGranted(Place place, User user) {
        if (place == null) {
            return true;
        }
        if (place.getIsPrivate()) {
            if (user != null) {
                User owner = place.getUser();
                if (owner == null) {
                    return false;
                }
                if (!owner.getId().equals(user.getId())) {
                    return false;
                }

            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public WriteCommentResult writeComment(long placeId, User user, CommentForm commentForm) throws 
            PermissionDeniedException, PlaceNotFoundException, InvalidFormException {
        if (user == null) {
            throw new IllegalArgumentException("User must be not null");
        }
        
        if (commentFormValidator.validate(commentForm)) {
            
            Place place = placeRepository.findOne(placeId);
            
            if (accessGranted(place, user)) {
                
                PlaceComment placeComment = new PlaceComment();
                placeComment.setUser(user);
                placeComment.setPlace(place);
                placeComment.setCommentDate(System.currentTimeMillis());
                placeComment.setBody(commentForm.getText());
                placeCommentRepository.save(placeComment);
                
                return new WriteCommentResult(placeComment.getId());
                
            } else {
                throw new PermissionDeniedException();
            }
            
        } else {
            throw new InvalidFormException("Invalid CommentForm");
        }
        
    }

    @Override
    public void removeComment(long commentId, User user) throws 
            PermissionDeniedException, NotFoundException, IllegalArgumentException {
        
        if (user == null) {
            throw new IllegalArgumentException("User must be not null");
        }
        
        PlaceComment placeComment = placeCommentRepository.findOne(commentId);
        
        if (placeComment != null) {
            
            User commentOwner = placeComment.getUser();
            
            if (commentOwner != null) {
                if (commentOwner.getId().equals(user.getId())) {
                    placeCommentRepository.delete(commentId);
                } else {
                    throw new PermissionDeniedException();
                }
            } else {
                throw new NotFoundException("Wtf? comment owner is null :(");
            }
            
        } else {
            throw new NotFoundException("Comment with id = " + commentId + " not found");
        }
        
    }

    @Override
    public PlaceLikeResult like(long placeId, User user) throws PermissionDeniedException, NotFoundException, IllegalArgumentException {
        
        if (user == null) {
            throw new IllegalArgumentException("User must be not null");
        }
        
        Place place = placeRepository.findOne(placeId);
        
        if (place != null) {
            
            if (accessGranted(place, user)) {
                
                boolean isLiked = false;
                
                PlaceLike placeLike = placeLikeRepository.findByPlaceAndUser(place, user);
                if (placeLike == null)  {
                    placeLike = new PlaceLike();
                    placeLike.setDate(System.currentTimeMillis());
                    placeLike.setPlace(place);
                    placeLike.setUser(user);
                    placeLikeRepository.save(placeLike);
                    isLiked = true;
                } else {
                    placeLikeRepository.delete(placeLike);
                    isLiked = false;
                }
                
                return new PlaceLikeResult(isLiked);
                
            } else {
                throw new PermissionDeniedException();
            }
            
        } else {
            throw new NotFoundException();
        }
        
    }
    
    
}
