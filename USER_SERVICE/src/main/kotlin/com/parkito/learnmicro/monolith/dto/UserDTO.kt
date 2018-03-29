package com.parkito.learnmicro.monolith.dto;

import com.parkito.learnmicro.monolith.entity.User
import java.io.Serializable
import java.util.*

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
data class UserDTO(
        val email: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val serials: List<String> = Collections.emptyList()) : Serializable {

    companion object {
        fun fromEntity(entity: User, serials: List<String>) = UserDTO(
                email = entity.email,
                firstName = entity.firstName,
                lastName = entity.lastName,
                serials = serials
        )

        fun toEntity(userDTO: UserDTO) = User(
                email = userDTO.email,
                firstName = userDTO.firstName,
                lastName = userDTO.lastName
        )
    }
}
