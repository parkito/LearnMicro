package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.controller.RestUserClient
import com.parkito.learnmicro.monolith.dto.UserDTO
import com.parkito.learnmicro.monolith.entity.User
import com.parkito.learnmicro.monolith.exception.value.BusinessLogicException
import com.parkito.learnmicro.monolith.exception.value.ResourceNotFoundException
import com.parkito.learnmicro.monolith.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var restUserClient: RestUserClient

    @Transactional
    fun createUser(email: String, firstName: String, secondName: String): UserDTO {
        val existedUser: User? = userRepository.findByEmail(email);
        if (existedUser == null) {
            val user = User(email = email, firstName = firstName, lastName = secondName)
            userRepository.save(user)
            return UserDTO.fromEntity(user, Collections.emptyList())
        } else {
            throw BusinessLogicException("User with email = $email already exists")
        }
    }

    fun findUserByEmail(email: String): UserDTO {
        val user: User? = userRepository.findByEmail(email)
        if (user != null) {
            val docs = restUserClient.getAllClientDocuments(email)
            var serials = Collections.emptyList<String>()
            if (docs != null) {
                serials = docs.map { d -> d.serial }.toCollection(serials)
            }
            return UserDTO.fromEntity(user, serials)
        } else {
            throw ResourceNotFoundException("User with email = $email wasn't found")
        }
    }

    @Transactional
    fun deleteUserByEmail(email: String) {
        userRepository.deleteByEmail(email)
    }
}
