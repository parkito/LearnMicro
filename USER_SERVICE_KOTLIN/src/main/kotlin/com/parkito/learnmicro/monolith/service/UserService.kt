package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.controller.RestUserClient
import com.parkito.learnmicro.monolith.dto.UserDTO
import com.parkito.learnmicro.monolith.entity.User
import com.parkito.learnmicro.monolith.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
class UserService(@Autowired userRepository: UserRepository,
                  @Autowired restUserClient: RestUserClient) {

    private static final String COMMA_SEPARATOR = ",";


    fun createUser(email: String, firstName: String, secondName: String): UserDTO {
        var existedUser: User = userRepository.findByEmail(email);
        if (existedUser == null) {
            User user = User . builder ()
                    .email(email)
                    .firstName(firstName)
                    .lastName(secondName)
                    .build();
            return convert(userRepository.save(user));
        } else {
            return null;
        }
    }

    fun findUserByEmail(email: String): UserDTO {
        val user: User = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        } else {
            return convert(user);
        }
    }

    fun deleteUserByEmail(email: String): Boolean {
        val userForDelete: User = userRepository.findByEmail(email);
        if (userForDelete != null) {
            userRepository.delete(userForDelete);
            return true;
        } else {
            return false;
        }
    }

    fun convert(user: User): UserDTO? {
        if (user == null) {
            return null;
        } else {
            return UserDTO.builder()
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .firstName(user.getLastName())
                    .serials(restUserClient.getAllClientDocuments(user.getEmail()).stream()
                            .map(d -> d.getSerial()+COMMA_SEPARATOR+d.getNumber())
            .collect(Collectors.toList()))
            .build();
        }
    }

    private User parse(UserDTO userDTO)
    {
        User user = userRepository . findByEmail (userDTO.getEmail());
        if (user == null) {
            return null;
        } else {
            return User.builder()
                    .email(userDTO.getEmail())
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .build();
        }
    }
}
