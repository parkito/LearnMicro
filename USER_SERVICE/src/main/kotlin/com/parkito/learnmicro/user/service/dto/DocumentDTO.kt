package com.parkito.learnmicro.user.service.dto;

import java.io.Serializable

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
data class DocumentDTO(
        val serial: String,
        val number: String,
        val email: String
) : Serializable

