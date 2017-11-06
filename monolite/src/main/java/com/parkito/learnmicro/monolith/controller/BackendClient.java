package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.ParcelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Component
public class BackendClient {

    private final RestTemplate restTemplate;

    private final String parcelServiceUrl;
    private final String findParcelByNumberPath;
    private final String findAllParcelsForUser;

    @Autowired
    public BackendClient(RestTemplate restTemplate,
                         @Value("${rest.post-service.api.path}") String parcelServiceUrl,
                         @Value("${rest.post-service.findByNumberPath}") String findParcelByNumberPath,
                         @Value("$rest.post-service.findAllParcelsForUser") String findAllParcelsForUser) {
        this.restTemplate = restTemplate;
        this.parcelServiceUrl = parcelServiceUrl;
        this.findParcelByNumberPath = findParcelByNumberPath;
        this.findAllParcelsForUser = findAllParcelsForUser;
    }

    public ParcelDTO getParcelByNumber(long number) {
        URI targetUrl = UriComponentsBuilder
                .fromUriString(parcelServiceUrl)
                .pathSegment(findParcelByNumberPath)
                .build().toUri();


        ResponseEntity<ParcelDTO> result = restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<ParcelDTO>() {
                });

        return result.getBody();
    }

    public List<ParcelDTO> getAllUserParcels(String email) {
        URI targetUrl = UriComponentsBuilder
                .fromUriString(parcelServiceUrl)
                .pathSegment(findAllParcelsForUser)
                .build().toUri();


        ResponseEntity<List<ParcelDTO>> result = restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<ParcelDTO>>() {
                });

        return result.getBody();
    }
}
