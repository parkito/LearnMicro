package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.controller.RestUserClient
import com.parkito.learnmicro.monolith.dto.UserDTO
import com.parkito.learnmicro.monolith.entity.User
import com.parkito.learnmicro.monolith.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
class UserService(@Autowired val userRepository: UserRepository,
                  @Autowired val restUserClient: RestUserClient) {

    val COMMA_SEPARATOR = ",";

    fun createUser(email: String, firstName: String, secondName: String): UserDTO? {
        var existedUser: User = userRepository.findByEmail(email);
        if (existedUser == null) {
            val user = User(userId = 1, email = email, firstName = firstName, lastName = secondName)
            userRepository.save(user)
            return convert(userRepository.save(user));
        } else {
            return null;
        }
    }

    fun findUserByEmail(email: String): UserDTO? {
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
            val allClientDocuments = restUserClient.getAllClientDocuments(user.email)!!
                    .map { d -> d.serial + COMMA_SEPARATOR + d.number }.toCollection(arrayListOf())

            return UserDTO(email = user.email, firstName = user.firstName, lastName = user.lastName, serials = allClientDocuments)

        }
    }
}
