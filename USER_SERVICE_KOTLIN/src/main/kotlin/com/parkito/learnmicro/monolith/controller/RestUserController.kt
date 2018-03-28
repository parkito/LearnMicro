package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.UserDTO
import com.parkito.learnmicro.monolith.service.UserService
import lombok.extern.log4j.Log4j2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Log4j2
@RestController
@RequestMapping("/api/v2")
class RestUserController {
    @Autowired
    lateinit var userService: UserService

    val headers: HttpHeaders = HttpHeaders();

    @GetMapping("/create-user")
    fun createUser(@RequestParam email: String,
                   @RequestParam firstName: String,
                   @RequestParam secondName: String): ResponseEntity<UserDTO> {
//        log.info("In createUser()");
        val user: UserDTO = userService.createUser(email, firstName, secondName);
        return ResponseEntity(user, HttpStatus.OK)
    }

    @GetMapping("/find-user")
    fun findUser(@RequestParam email: String): ResponseEntity<UserDTO> {
//        log.info("In findUser()");
        return ResponseEntity(userService.findUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/delete-user")
    fun deleteUser(@RequestParam email: String): ResponseEntity<Boolean> {
//        log.info("In deleteUser()");
        userService.deleteUserByEmail(email);
        return ResponseEntity(true, HttpStatus.OK)
    }
}
