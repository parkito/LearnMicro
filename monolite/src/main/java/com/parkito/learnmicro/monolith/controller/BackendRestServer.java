package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.DocumentDTO;
import com.parkito.learnmicro.monolith.dto.ParcelDTO;
import com.parkito.learnmicro.monolith.service.DocumentService;
import com.parkito.learnmicro.monolith.service.ParcelService;
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

import java.util.List;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@RestController
@RequestMapping("/api/v1")
public class BackendRestServer {
    private final UserService userService;
    private final ParcelService parcelService;
    private final DocumentService documentService;

    @Autowired
    public BackendRestServer(UserService userService, ParcelService parcelService, DocumentService documentService) {
        this.userService = userService;
        this.parcelService = parcelService;
        this.documentService = documentService;
    }

    private final HttpHeaders headers = new HttpHeaders();

    @RequestMapping(path = "/create-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<org.springframework.http.HttpEntity<?>> createUser(@RequestParam String email,
                                                                             @RequestParam String firstName,
                                                                             @RequestParam String secondName) {
        boolean isUserCreated = userService.createUser(email, firstName, secondName);
        headers.clear();
        if (isUserCreated) {
            headers.add("Status", "User created");
        } else {
            headers.add("Status", "User wasn't created");
        }
        return isUserCreated ? new ResponseEntity<>(ResponseEntity.EMPTY, headers, HttpStatus.OK) :
                new ResponseEntity<>(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/delete-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<org.springframework.http.HttpEntity<?>> deleteUser(@RequestParam String email) {
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

    @RequestMapping(path = "/create-parcel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<org.springframework.http.HttpEntity<?>> createParcel(@RequestParam String email) {
        boolean isUserDeleted = userService.deleteUserByEmail(email);
        return isUserDeleted ? new ResponseEntity<>(ResponseEntity.EMPTY, headers, HttpStatus.OK) :
                new ResponseEntity<>(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/find-parcel-by-number", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParcelDTO> findParcelByNumber(@RequestParam long number) {
        ParcelDTO parcel = parcelService.findParcelByNumber(number);
        headers.clear();
        if (parcel == null) {
            headers.add("Status", "Can't find parcel");
        } else {
            headers.add("Status", "Parcel found");
        }
        return parcel == null ? new ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(parcel, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "/find-all-parcels-for-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParcelDTO>> findAllParcelsForUser(@RequestParam String email) {
        List<ParcelDTO> allParcelsForUser = parcelService.getAllParcelsForUser(email);
        headers.clear();
        if (allParcelsForUser.isEmpty()) {
            headers.add("Status", "Nothing found");
        } else {
            headers.add("Status", "Data found");
        }
        return allParcelsForUser.isEmpty() ? new ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(allParcelsForUser, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "/create-document", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentDTO> createDocument(@RequestParam String serial,
                                                      @RequestParam String number,
                                                      @RequestParam String email) {
        DocumentDTO document = documentService.createDocument(serial, number, email);
        headers.clear();
        if (document == null) {
            headers.add("Status", "Can't created document");
        } else {
            headers.add("Status", "Document created");
        }
        return document == null ? new ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(document, headers, HttpStatus.OK);
    }
}
