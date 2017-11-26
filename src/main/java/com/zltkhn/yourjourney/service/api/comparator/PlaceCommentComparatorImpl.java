/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.comparator;

import com.zltkhn.yourjourney.entities.PlaceComment;
import org.springframework.stereotype.Component;

/**
 *
 * @author rtmss
 */
@Component
public class PlaceCommentComparatorImpl implements PlaceCommentComparator{
    @Override
    public int compare(PlaceComment o1, PlaceComment o2) {
        if (o1 == null) {
            return 1;
        } else {
            if (o2 == null) {
                return -1;
            } else {
                if (o1.getCommentDate() > o2.getCommentDate()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }
}
