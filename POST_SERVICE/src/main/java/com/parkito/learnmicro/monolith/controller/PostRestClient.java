package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.DocumentDTO;
import com.parkito.learnmicro.monolith.dto.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Log4j2
@Component
public class PostRestClient {
    private final RestTemplate restTemplate;
    private final String apiDocumentServiceApiUrl;
    private final String apiUserServiceApiUrl;
    private final String findUserByEmailPath;
    private final String findDocumentBySerialAndNumber;
    private final String forcedUserRemovingPath;
    private final String forcedDocumentRemovingPath;

    @Autowired
    public PostRestClient(RestTemplate restTemplate,
                          @Value("${rest.document-service.api.path}") String apiDocumentServiceApiUrl,
                          @Value("${rest.user-service.api.path}") String apiUserServiceApiUrl,
                          @Value("${rest.post-service.find-user-by-email.path}") String findUserByEmailPath,
                          @Value("${rest.post-service.find-documents-by-serial-and-number.path}") String findDocumentBySerialAndNumber,
                          @Value("${rest.user-service.forced-user-removing-path}") String forcedUserRemovingPath,
                          @Value("${rest.document-service.forced-document-removing-path}") String forcedDocumentRemovingPath) {
        this.restTemplate = restTemplate;
        this.apiDocumentServiceApiUrl = apiDocumentServiceApiUrl;
        this.apiUserServiceApiUrl = apiUserServiceApiUrl;
        this.findUserByEmailPath = findUserByEmailPath;
        this.findDocumentBySerialAndNumber = findDocumentBySerialAndNumber;
        this.forcedUserRemovingPath = forcedUserRemovingPath;
        this.forcedDocumentRemovingPath = forcedDocumentRemovingPath;
    }


    public UserDTO findUserByEmail(String email) {
        log.info("findUserByEmail {}", email);
        URI targetUrl = UriComponentsBuilder
                .fromHttpUrl(apiUserServiceApiUrl)
                .pathSegment(findUserByEmailPath)
                .queryParam("email", email)
                .build().toUri();
        try {
            return restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                    new ParameterizedTypeReference<UserDTO>() {
                    }).getBody();
        } catch (Exception ex) {
            log.error("Goes wrong during USER_SERVICE {} connection !!!", targetUrl, ex);
            return null;
        }
    }

    public DocumentDTO findDocumentBySerialAndNumber(String serial, String number) {
        log.info("findDocumentBySerialAndNumber {}, {}", serial, number);
        URI targetUrl = UriComponentsBuilder
                .fromHttpUrl(apiDocumentServiceApiUrl)
                .pathSegment(findDocumentBySerialAndNumber)
                .queryParam("serial", serial)
                .queryParam("number", number)
                .build().toUri();
        try {
            return restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                    new ParameterizedTypeReference<DocumentDTO>() {
                    }).getBody();
        } catch (Exception ex) {
            log.error("Goes wrong during DOCUMENT_SERVICE {} connection !!!", targetUrl, ex);
            return null;
        }
    }

    public boolean forceUserRemove(String email) {
        log.info("forceUserRemove {}", email);
        URI targetUrl = UriComponentsBuilder
                .fromHttpUrl(apiUserServiceApiUrl)
                .pathSegment(forcedUserRemovingPath)
                .queryParam("email", email)
                .build().toUri();
        try {
            return restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                    Boolean.class).getBody();
        } catch (Exception ex) {
            log.error("Goes wrong during USER_SERVICE {} connection !!!", targetUrl, ex);
            return false;
        }
    }

    public boolean forceDocumentRemove(String email) {
        log.info("forceDocumentRemove {}", email);
        URI targetUrl = UriComponentsBuilder
                .fromHttpUrl(apiUserServiceApiUrl)
                .pathSegment(forcedDocumentRemovingPath)
                .queryParam("email", email)
                .build().toUri();
        try {
            return restTemplate.exchange(targetUrl, HttpMethod.GET, HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Boolean>() {
                    }).getBody();
        } catch (Exception ex) {
            log.error("Goes wrong during DOCUMENT_SERVICE {} connection !!!", targetUrl, ex);
            return false;
        }
    }
}
