package com.parkito.learnmicro.user.service.controller;

import com.parkito.learnmicro.user.service.dto.DocumentDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Component
class RestUserClient(
        @Autowired val restTemplate: RestTemplate,
        @Value("\${rest.document-service.api.path}") val apiDocumentServiceApiUrl: String,
        @Value("\${rest.post-service.find-all-documents-for-customer}") val findAllDocumentsForUserPath: String) {

    fun getAllClientDocuments(email: String): List<DocumentDTO>? {
        val targetUrl: URI = UriComponentsBuilder
                .fromHttpUrl(apiDocumentServiceApiUrl)
                .pathSegment(findAllDocumentsForUserPath)
                .queryParam("email", email)
                .build().toUri();

        try {
            //todo null safety kotlin style
            return restTemplate.exchange(
                    targetUrl,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    object : ParameterizedTypeReference<List<DocumentDTO>>() {}
            ).body;
        } catch (ex: Exception) {
            return Collections.emptyList();
        }
    }
}
