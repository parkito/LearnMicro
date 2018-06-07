package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.DocumentDTO;
import com.parkito.learnmicro.monolith.dto.ParcelDTO;
import com.parkito.learnmicro.monolith.dto.UserDTO;
import com.parkito.learnmicro.monolith.entity.Parcel;
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
public class BackendRestController {
    private final UserService userService;
    private final ParcelService parcelService;
    private final DocumentService documentService;

    @Autowired
    public BackendRestController(UserService userService, ParcelService parcelService, DocumentService documentService) {
        this.userService = userService;
        this.parcelService = parcelService;
        this.documentService = documentService;
    }

    private final HttpHeaders headers = new HttpHeaders();

    @RequestMapping(path = "/create-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestParam String email,
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

    @RequestMapping(path = "/create-parcel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParcelDTO> createParcel(@RequestParam long number,
                                                  @RequestParam double price,
                                                  @RequestParam double weight,
                                                  @RequestParam String emailFrom,
                                                  @RequestParam String emailTo) {
        ParcelDTO parcel = parcelService.createParcel(number, price, weight, emailFrom, emailTo, Parcel.Status.IN_PROCESS.getCode());
        headers.clear();
        if (parcel != null) {
            headers.add("Status", "Parcel created");
        } else {
            headers.add("Status", "Parcel wasn't created");
        }
        return parcel == null ? new ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(parcel, headers, HttpStatus.OK);
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

    @RequestMapping(path = "/find-document", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentDTO> findDocument(@RequestParam String serial,
                                                    @RequestParam String number) {
        DocumentDTO document = documentService.findDocument(serial, number);
        headers.clear();
        if (document == null) {
            headers.add("Status", "Document wasn't found");
        } else {
            headers.add("Status", "Document found");
        }
        return document == null ? new ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(document, headers, HttpStatus.OK);
    }

    @RequestMapping(path = "/get-documents-for-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentDTO>> findDocument(@RequestParam String email) {
        List<DocumentDTO> documents = documentService.findAllDocumentsForUser(email);
        headers.clear();
        if (documents == null) {
            headers.add("Status", "Documents wasn't found");
        } else {
            headers.add("Status", "Documents found");
        }
        return documents == null ? new ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(documents, headers, HttpStatus.OK);
    }


    @RequestMapping(path = "/delete-document", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteDocument(@RequestParam String serial,
                                         @RequestParam String number) {
        boolean isDocumentDeleted = documentService.deleteDocument(serial, number);
        headers.clear();
        if (isDocumentDeleted) {
            headers.add("Status", "Document deleted");
        } else {
            headers.add("Status", "Document wasn't deleted");
        }
        return isDocumentDeleted ? new ResponseEntity<>(ResponseEntity.EMPTY, headers, HttpStatus.OK) :
                new ResponseEntity<>(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/get-parcel-for-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParcelDTO> getParcelForUser(@RequestParam long parcelNumber,
                                                      @RequestParam String email,
                                                      @RequestParam String docSerial,
                                                      @RequestParam String docNumber) {
        ParcelDTO parcel = parcelService.getParcelForUser(parcelNumber, email, docSerial, docNumber);
        headers.clear();
        if (parcel == null) {
            headers.add("Status", "Can't get parcel for user");
        } else {
            headers.add("Status", "Parcel for user was delivered");
        }
        return parcel == null ? new ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(parcel, headers, HttpStatus.OK);
    }

}
