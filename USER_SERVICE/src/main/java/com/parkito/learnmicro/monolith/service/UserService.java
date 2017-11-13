package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.dto.UserDTO;
import com.parkito.learnmicro.monolith.entity.User;
import com.parkito.learnmicro.monolith.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public UserDTO createUser(String email, String firstName, String secondName) {
        User existedUser = userRepository.findByEmail(email);
        if (existedUser == null) {
            User user = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(secondName)
                    .build();
            return convert(userRepository.save(user));
        } else {
            return null;
        }
    }

    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        } else {
            return convert(user);
        }
    }

    private UserDTO convert(User user) {
        if (user == null) {
            return null;
        } else {
            return UserDTO.builder()
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .firstName(user.getLastName())
                    .build();
        }
    }
}
