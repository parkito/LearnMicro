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
class RestUserController(@Autowired val userService: UserService) {

    val headers: HttpHeaders = HttpHeaders();

    @GetMapping("/create-user")
    fun createUser(@RequestParam email: String,
                   @RequestParam firstName: String,
                   @RequestParam secondName: String): ResponseEntity<UserDTO?> {
//        log.info("In createUser()");
        var user: UserDTO? = userService.createUser(email, firstName, secondName);
        headers.clear();
        if (user != null) {
            headers.add("Status", "User created");
        } else {
            headers.add("Status", "User wasn't created");
        }
        return ResponseEntity(user, headers, HttpStatus.OK)
//            return ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find-user")
    fun findUser(@RequestParam email: String): ResponseEntity<UserDTO?> {
//        log.info("In findUser()");
        val user: UserDTO? = userService.findUserByEmail(email);
        headers.clear();
        if (user == null) {
            headers.add("Status", "User wasn't found");
        } else {
            headers.add("Status", "User found");
        }
//            return ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST)
        return ResponseEntity(user, headers, HttpStatus.OK);
    }

    @GetMapping("/delete-user")
    fun deleteUser(@RequestParam email: String): ResponseEntity<Boolean> {
//        log.info("In deleteUser()");
        val isUserDeleted: Boolean = userService.deleteUserByEmail(email);
        headers.clear();
        if (isUserDeleted) {
            headers.add("Status", "User deleted");
        } else {
            headers.add("Status", "User wasn't deleted");
        }
        return ResponseEntity(isUserDeleted, headers, HttpStatus.OK)
//        return isUserDeleted ? new ResponseEntity<>(ResponseEntity.EMPTY, headers, HttpStatus.OK) :
//        new ResponseEntity < > (ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST);
    }
}
