package com.parkito.learnmicro.monolith.controller;

import com.parkito.learnmicro.monolith.dto.DocumentDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Component
class RestUserClient(
        @Autowired val restTemplate: RestTemplate,
        @Value("\${rest.document - service.api.path}") val apiDocumentServiceApiUrl: String,
        @Value("\${rest.post-service.find-all-documents-for-customer}") val findAllDocumentsForUserPath: String) {

    fun getAllClientDocuments(email: String): List<DocumentDTO>? {
        val targetUrl: URI = UriComponentsBuilder
                .fromHttpUrl(apiDocumentServiceApiUrl)
                .pathSegment(findAllDocumentsForUserPath)
                .queryParam("email", email)
                .build().toUri();

        try {
            return restTemplate.exchange(
                    targetUrl,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    object : ParameterizedTypeReference<List<DocumentDTO>>() {}
            ).getBody();
        } catch (ex: Exception) {
            return null;
        }
    }
}
