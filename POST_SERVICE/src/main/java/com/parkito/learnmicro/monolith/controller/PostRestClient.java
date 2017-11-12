package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.DocumentDTO;
import com.parkito.learnmicro.monolith.dto.UserDTO;
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

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Component
public class PostRestClient {
    private final RestTemplate restTemplate;
    private final String apiDocumentServiceApiUrl;
    private final String apiUserServiceApiUrl;
    private final String findUserByEmailPath;
    private final String findAllUserDocumentsPath;

    @Autowired
    public PostRestClient(RestTemplate restTemplate,
                          @Value("${rest.document-service.api.path}") String apiDocumentServiceApiUrl,
                          @Value("${rest.user-service.api.path}") String apiUserServiceApiUrl,
                          @Value("$rest.post-service.find-user-by-email-path") String findUserByEmailPath,
                          @Value("$rest.post-service.find-all-documents-for-user") String findAllUserDocuments) {
        this.restTemplate = restTemplate;
        this.apiDocumentServiceApiUrl = apiDocumentServiceApiUrl;
        this.apiUserServiceApiUrl = apiUserServiceApiUrl;
        this.findUserByEmailPath = findUserByEmailPath;
        this.findAllUserDocumentsPath = findAllUserDocuments;
    }


    public UserDTO findUserByEmail(String email) {
        URI targetUrl = UriComponentsBuilder
                .fromUriString(apiUserServiceApiUrl)
                .pathSegment(findUserByEmailPath)
                .queryParam(email)
                .build().toUri();

        ResponseEntity<UserDTO> result = restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<UserDTO>() {
                });

        return result.getBody();
    }

    public DocumentDTO findDocumentBySerialAndNumber(String serial, String number) {
        URI targetUrl = UriComponentsBuilder
                .fromUriString(apiDocumentServiceApiUrl)
                .pathSegment(findAllUserDocumentsPath)
                .queryParam("serial", serial)
                .queryParam("number", number)
                .build().toUri();

        ResponseEntity<DocumentDTO> result = restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<DocumentDTO>() {
                });

        return result.getBody();
    }
}
