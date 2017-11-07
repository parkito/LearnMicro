package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.dto.UserDTO;
import com.parkito.learnmicro.monolith.entity.User;
import com.parkito.learnmicro.monolith.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User convert(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null) {
            return User.builder()
                    .email(userDTO.getEmail())
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .build();
        } else {
            return null;
        }
    }

    public boolean createUser(String email, String firstName, String secondName) {
        User existedUser = userRepository.findByEmail(email);
        if (existedUser == null) {
            User user = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(secondName)
                    .build();
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean deleteUserByEmail(String email) {
        User userForDelete = userRepository.findByEmail(email);
        if (userForDelete != null) {
            userRepository.delete(userForDelete);
            return true;
        } else {
            return false;
        }
    }
}
