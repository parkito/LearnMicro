package com.parkito.learnmicro.user.service.exception.value

/**
 * @author Artem Karnov @date 3/28/2018.
 * @email  artem.karnov@t-systems.com
 */
class BusinessLogicException : RuntimeException {
     var developerMessage: String = ""

    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?, developerMessage: String, cause: Throwable?) : super(message, cause) {
        this.developerMessage = developerMessage
    }

    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)
}