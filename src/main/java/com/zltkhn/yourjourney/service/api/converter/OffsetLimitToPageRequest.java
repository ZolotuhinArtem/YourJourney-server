/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.service.api.converter;

import org.springframework.data.domain.PageRequest;

/**
 *
 * @author rtmss
 */
public interface OffsetLimitToPageRequest {
    
    PageRequest convert(long offset, long limit);
    
}
