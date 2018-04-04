package com.parkito.learnmicro.user.service.exception.handler

import com.parkito.learnmicro.user.service.exception.detail.ErrorDetails
import com.parkito.learnmicro.user.service.exception.value.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


/**
 * @author Artem Karnov @date 3/28/2018.
 * @email  artem.karnov@t-systems.com
 */
@ControllerAdvice
class ResourceNotFoundExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleUserNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ErrorDetails> {
        val errorDetails = ErrorDetails(ex.message!!, ex.developerMessage)
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }
}