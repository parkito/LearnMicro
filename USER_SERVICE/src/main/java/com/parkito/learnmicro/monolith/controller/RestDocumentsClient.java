package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.DocumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
public class RestDocumentsClient {
    private final RestTemplate restTemplate;
    private final String apiDocumentServiceApiUrl;
    private final String findAllDocuments;

    @Autowired
    public RestDocumentsClient(RestTemplate restTemplate,
                               @Value("${rest.document-service.api.path}") String apiDocumentServiceApiUrl,
                               @Value("${rest.document-service.find-all-documents}") String findAllDocuments) {
        this.restTemplate = restTemplate;
        this.apiDocumentServiceApiUrl = apiDocumentServiceApiUrl;
        this.findAllDocuments = findAllDocuments;
    }

    public List<DocumentDTO> getAllClientDocuments() {
        URI targetUrl = UriComponentsBuilder
                .fromHttpUrl(apiDocumentServiceApiUrl)
                .pathSegment(findAllDocuments)
                .build().toUri();

        try {
            return restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                    new ParameterizedTypeReference<List<DocumentDTO>>() {
                    }).getBody();
        } catch (Exception ex) {
            return null;
        }
    }
}
