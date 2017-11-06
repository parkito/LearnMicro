package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.entity.User;
import com.parkito.learnmicro.monolith.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean createUser(String email, String firstName, String secondName) {
        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(secondName)
                .build();
        try {
            userRepository.save(user);
            return true;
        } catch (PersistenceException ex) {
            return false;
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean deleteUserByEmail(String email) {
        try {
            userRepository.deleteByEmail(email);
            return true;
        }catch (PersistenceException ex){
            return false;
        }
    }
}
