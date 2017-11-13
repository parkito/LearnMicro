package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.ParcelDTO;
import com.parkito.learnmicro.monolith.entity.Parcel;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Log4j2
public class ForcedRestController {
    private final PostRestClient postRestClient;

    public ForcedRestController(PostRestClient postRestClient) {
        this.postRestClient = postRestClient;
    }

    private final HttpHeaders headers = new HttpHeaders();


    @RequestMapping(path = "/force-user-removing", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> forceUserRemoving(@RequestParam String email) {
        log.info("In createParcel()");
        boolean isUserRemoved = postRestClient.forceUserRemove(email);
        boolean isDocumentRemoved = postRestClient.forceDocumentRemove(email);
        headers.clear();
        boolean forcedResult = isUserRemoved && isDocumentRemoved;
        if (forcedResult) {
            headers.add("Status", "Success");
        } else {
            headers.add("Status", "Something went wrong");
        }

        return !forcedResult ? new ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.BAD_REQUEST) :
                new ResponseEntity(ResponseEntity.EMPTY, headers, HttpStatus.OK);
    }
}
