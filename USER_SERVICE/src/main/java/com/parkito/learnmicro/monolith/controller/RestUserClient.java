package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.DocumentDTO;
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
public class RestUserClient {
    private final RestTemplate restTemplate;
    private final String apiDocumentServiceApiUrl;
    private final String findAllDocumentsForUserPath;

    @Autowired
    public RestUserClient(RestTemplate restTemplate,
                          @Value("${rest.document-service.api.path}") String apiDocumentServiceApiUrl,
                          @Value("${rest.post-service.find-all-documents-for-customer}") String findAllDocumentsForUserPath) {
        this.restTemplate = restTemplate;
        this.apiDocumentServiceApiUrl = apiDocumentServiceApiUrl;
        this.findAllDocumentsForUserPath = findAllDocumentsForUserPath;
    }


    public List<DocumentDTO> getAllClientDocuments(String email) {
        URI targetUrl = UriComponentsBuilder
                .fromUriString(apiDocumentServiceApiUrl)
                .pathSegment(findAllDocumentsForUserPath)
                .queryParam("email", email)
                .build().toUri();


        ResponseEntity<List<DocumentDTO>> result = restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<DocumentDTO>>() {
                });

        return result.getBody();
    }
}
