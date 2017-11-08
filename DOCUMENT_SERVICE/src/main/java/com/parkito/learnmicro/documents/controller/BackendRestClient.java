package com.parkito.learnmicro.documents.controller;

import com.parkito.learnmicro.documents.dto.DocumentDTO;
import com.parkito.learnmicro.documents.dto.ParcelDTO;
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
public class BackendRestClient {
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String findAllParcelsForUserPath;
    private final String findAllUserDocumentsPath;

    @Autowired
    public BackendRestClient(RestTemplate restTemplate,
                             @Value("${rest.post-service.api.path}") String apiUrl,
                             @Value("${rest.post-service.findByNumberPath}") String findParcelByNumberPath,
                             @Value("$rest.post-service.findAllParcelsForUserPath") String findAllParcelsForUser,
                             @Value("$rest.post-service.findAllUserDocumentsPath") String findAllUserDocuments) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.findAllParcelsForUserPath = findAllParcelsForUser;
        this.findAllUserDocumentsPath = findAllUserDocuments;
    }


    public List<ParcelDTO> getAllUserParcels(String email) {
        URI targetUrl = UriComponentsBuilder
                .fromUriString(apiUrl)
                .pathSegment(findAllParcelsForUserPath)
                .build().toUri();


        ResponseEntity<List<ParcelDTO>> result = restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<ParcelDTO>>() {
                });

        return result.getBody();
    }

    public List<DocumentDTO> getAllClientDocuments(String email) {
        URI targetUrl = UriComponentsBuilder
                .fromUriString(apiUrl)
                .pathSegment(findAllUserDocumentsPath)
                .build().toUri();


        ResponseEntity<List<DocumentDTO>> result = restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<DocumentDTO>>() {
                });

        return result.getBody();
    }
}
