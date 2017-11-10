package com.parkito.learnmicro.documents.controller;

import com.parkito.learnmicro.documents.dto.UserDTO;
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
public class RestDocumentClient {
    private final RestTemplate restTemplate;
    private final String apiUserServiceApiUrl;
    private final String findUserByEmailPath;

    @Autowired
    public RestDocumentClient(RestTemplate restTemplate,
                              @Value("${rest.user-service.api.path}") String apiUserServiceApiUrl,
                              @Value("${rest.user-service.find-by-email}") String findUserByEmailPath) {
        this.restTemplate = restTemplate;
        this.apiUserServiceApiUrl = apiUserServiceApiUrl;
        this.findUserByEmailPath = findUserByEmailPath;
    }

    public UserDTO findUserByEmail(String email) {
        URI targetUrl = UriComponentsBuilder
                .fromUriString(apiUserServiceApiUrl)
                .pathSegment(findUserByEmailPath)
                .queryParam("email", email)
                .build().toUri();

        ResponseEntity<UserDTO> result = restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<UserDTO>() {
                });

        return result.getBody();
    }
}
