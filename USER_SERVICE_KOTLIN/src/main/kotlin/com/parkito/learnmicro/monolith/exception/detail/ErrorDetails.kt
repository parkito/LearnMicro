package com.parkito.learnmicro.monolith.exception.detail

import java.io.Serializable
import java.util.*

/**
 * @author Artem Karnov @date 3/28/2018.
 * @email  artem.karnov@t-systems.com
 */
data class ErrorDetails(
        var date: Date,
        var message: String,
        var developerMessage: String) : Serializable {

    constructor(message: String, developerMessage: String) : this(Date(), message, developerMessage)
}