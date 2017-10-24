/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zltkhn.yourjourney.repository;

import com.zltkhn.yourjourney.entities.User;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rtmss
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    User findOneByLoginAndPasswordCrypt(String login, String passwordCrypt);
    
}
