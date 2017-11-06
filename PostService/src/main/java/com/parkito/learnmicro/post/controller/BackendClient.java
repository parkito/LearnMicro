package com.parkito.learnmicro.post.controller;

import com.parkito.learnmicro.post.dto.ParcelDTO;
import com.parkito.learnmicro.post.entity.Parcel;
import com.parkito.learnmicro.post.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@RestController
@RequestMapping("/api/v1/")
public class BackendClient {

    private final ParcelService parcelService;

    @Autowired
    public BackendClient(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @RequestMapping(value = "/find-by-number", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Parcel> getParcelByNumber(@RequestParam long number) {
        return new ResponseEntity<>(parcelService.findParcelByNumber(number), OK);
    }

    @RequestMapping(value = "/find-all-parcels-for-user", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParcelDTO>> getAllUserParcels(@RequestParam String email) {
        return new ResponseEntity<List<ParcelDTO>>(parcelService.getAllParcelsForUser(email), OK);
    }
}
