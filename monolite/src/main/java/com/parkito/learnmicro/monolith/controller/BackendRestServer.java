package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.DocumentDTO;
import com.parkito.learnmicro.monolith.dto.ParcelDTO;
import com.parkito.learnmicro.monolith.service.DocumentService;
import com.parkito.learnmicro.monolith.service.ParcelService;
import com.parkito.learnmicro.monolith.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
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

    @RequestMapping(path = "/create-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestParam String email,
                                     @RequestParam String firstName,
                                     @RequestParam String secondName) {
        boolean isUserCreated = userService.createUser(email, firstName, secondName);
        return isUserCreated ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/delete-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@RequestParam String email) {
        boolean isUserDeleted = userService.deleteUserByEmail(email);
        return isUserDeleted ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/find-parcel-by-number", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParcelDTO> findParcelByNumber(@RequestParam long number) {
        ParcelDTO parcel = parcelService.findParcelByNumber(number);
        return parcel == null ? new ResponseEntity(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(parcel, HttpStatus.OK);
    }

    @RequestMapping(path = "/find-all-parcels-for-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParcelDTO>> findAllParcelsForUser(@RequestParam String email) {
        List<ParcelDTO> allParcelsForUser = parcelService.getAllParcelsForUser(email);
        return allParcelsForUser.isEmpty() ? new ResponseEntity(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(allParcelsForUser, HttpStatus.OK);
    }

    @RequestMapping(path = "/create-document", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentDTO> createDocument(@RequestParam String serial,
                                                      @RequestParam String number,
                                                      @RequestParam String email) {
        DocumentDTO document = documentService.createDocument(serial, number, email);
        return document == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(document, HttpStatus.OK);
    }
}
