package com.parkito.learnmicro.monolith.dto;

import java.io.Serializable

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
data class UserDTO(
        val email: String,
        val firstName: String,
        val lastName: String,
        val serials: List<String>
) : Serializable
