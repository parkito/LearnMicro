package com.parkito.learnmicro.user.service.exception.value;

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * @author Artem Karnov @date 3/28/2018.
 * @email artem.karnov@t-systems.com
 */
class ResourceNotFoundException : RuntimeException {
    var developerMessage: String = ""

    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}
