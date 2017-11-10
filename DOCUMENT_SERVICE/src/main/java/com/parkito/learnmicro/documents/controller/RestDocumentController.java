package com.parkito.learnmicro.documents.controller;

import com.parkito.learnmicro.documents.dto.DocumentDTO;
import com.parkito.learnmicro.documents.service.DocumentService;
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
 * @author Artem Karnov @date 11/10/2017.
 * artem.karnov@t-systems.com
 */
@RestController
@RequestMapping("/api/v1")
public class RestDocumentController {
    private final DocumentService documentService;

    @Autowired
    public RestDocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    private final HttpHeaders headers = new HttpHeaders();

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

}
