package com.parkito.learnmicro.monolith.dto;

import lombok.Builder
import lombok.Data
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

