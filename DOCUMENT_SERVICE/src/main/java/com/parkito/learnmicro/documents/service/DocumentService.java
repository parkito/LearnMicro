package com.parkito.learnmicro.documents.service;

import com.parkito.learnmicro.documents.controller.RestDocumentClient;
import com.parkito.learnmicro.documents.dto.DocumentDTO;
import com.parkito.learnmicro.documents.dto.UserDTO;
import com.parkito.learnmicro.documents.entity.Document;
import com.parkito.learnmicro.documents.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
public class DocumentService {
    private final RestDocumentClient restDocumentClient;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(RestDocumentClient restDocumentClient, DocumentRepository documentRepository) {
        this.restDocumentClient = restDocumentClient;
        this.documentRepository = documentRepository;
    }

    public DocumentDTO createDocument(String serial, String number, String email) {
        Document document = documentRepository.findBySerialAndNumber(serial, number);
        UserDTO user = restDocumentClient.findUserByEmail(email);
        if (document != null || user == null) {
            return null;
        } else {
            Document documentForPersisting = Document.builder()
                    .serial(serial)
                    .number(number)
                    .userEmail(user.getEmail())
                    .build();
            documentForPersisting = documentRepository.save(documentForPersisting);
            return convert(documentForPersisting);
        }
    }

    public DocumentDTO findDocument(String serial, String number) {
        Document document = documentRepository.findBySerialAndNumber(serial, number);
        if (document == null) {
            return null;
        } else {
            return DocumentDTO.builder()
                    .serial(document.getSerial())
                    .number(document.getNumber())
                    .email(document.getUserEmail())
                    .build();
        }
    }

    public boolean deleteDocument(String serial, String number) {
        Document document = documentRepository.findBySerialAndNumber(serial, number);
        if (document != null) {
            documentRepository.delete(document);
            return true;
        }
        return false;
    }

    private DocumentDTO convert(Document document) {
        if (document != null) {
            return DocumentDTO.builder()
                    .number(document.getNumber())
                    .serial(document.getSerial())
                    .email(document.getUserEmail())
                    .build();
        } else {
            return null;
        }
    }

    public List<DocumentDTO> findAllDocumentsForUser(String email) {
        List<Document> documents = documentRepository.findByUserEmail(email);
        if (documents == null) {
            return null;
        } else {
            return documents.stream()
                    .map(this::convert)
                    .collect(Collectors.toList());
        }
    }
}
