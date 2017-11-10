package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.UserDTO;
import com.parkito.learnmicro.monolith.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@RestController
@RequestMapping("/api/v1")
public class RestUserController {
    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    private final HttpHeaders headers = new HttpHeaders();

    @RequestMapping(path = "/create-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestParam String email,
                                     @RequestParam String firstName,
                                     @RequestParam String secondName) {
        UserDTO user = userService.createUser(email, firstName, secondName);
        headers.clear();
        if (user != null) {
            headers.add("Status", "User created");
        } else {
            headers.add("Status", "User wasn't created");
        }
        return user != null ? new ResponseEntity<>(user, headers, HttpStatus.OK) :
                new ResponseEntity<>(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/find-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findUser(@RequestParam String email) {
        UserDTO user = userService.findUserByEmail(email);
        headers.clear();
        if (user == null) {
            headers.add("Status", "User wasn't found");
        } else {
            headers.add("Status", "User found");
        }
        return user == null ? new ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "/delete-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@RequestParam String email) {
        boolean isUserDeleted = userService.deleteUserByEmail(email);
        headers.clear();
        if (isUserDeleted) {
            headers.add("Status", "User deleted");
        } else {
            headers.add("Status", "User wasn't deleted");
        }
        return isUserDeleted ? new ResponseEntity<>(ResponseEntity.EMPTY, headers, HttpStatus.OK) :
                new ResponseEntity<>(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST);
    }
}
