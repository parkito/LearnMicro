package com.parkito.learnmicro.post.controller;

import com.parkito.learnmicro.post.dto.ParcelDTO;
import com.parkito.learnmicro.post.entity.Parcel;
import com.parkito.learnmicro.post.service.ParcelService;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@RestController
@RequestMapping("/api/v1")
public class PostRestController {
    private final ParcelService parcelService;

    @Autowired
    public PostRestController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    private final HttpHeaders headers = new HttpHeaders();

    @RequestMapping(path = "/create-parcel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParcelDTO> createParcel(@RequestParam long number,
                                                  @RequestParam double price,
                                                  @RequestParam double weight,
                                                  @RequestParam String emailFrom,
                                                  @RequestParam String emailTo) {
        log.info("In createParcel()");
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
        log.info("In findParcelByNumber()");
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
        log.info("In findAllParcelsForUser()");
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

    @RequestMapping(path = "/get-parcel-for-user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParcelDTO> getParcelForUser(@RequestParam long parcelNumber,
                                                      @RequestParam String email,
                                                      @RequestParam String docSerial,
                                                      @RequestParam String docNumber) {
        log.info("In getParcelForUser()");
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
