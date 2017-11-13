package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.controller.RestDocumentsClient;
import com.parkito.learnmicro.monolith.dto.DocumentDTO;
import com.parkito.learnmicro.monolith.entity.Document;
import com.parkito.learnmicro.monolith.entity.User;
import com.parkito.learnmicro.monolith.repository.DocumentRepository;
import com.parkito.learnmicro.monolith.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
public class DocumentService {
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final RestDocumentsClient restDocumentsClient;

    @Autowired
    public DocumentService(UserRepository userRepository, DocumentRepository documentRepository, RestDocumentsClient restDocumentsClient) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.restDocumentsClient = restDocumentsClient;
    }

    public List<DocumentDTO> saveAllDocuments() {
        List<Document> documentsForPersisting = restDocumentsClient.getAllClientDocuments().stream()
                .filter(Objects::nonNull)
                .map(this::parse)
                .collect(Collectors.toList());

        return documentRepository.saveAll(documentsForPersisting).stream()
                .map(this::convert).collect(Collectors.toList());
    }

    public DocumentDTO createDocument(String serial, String number, String email) {
        Document document = documentRepository.findBySerialAndNumber(serial, number);
        User user = userRepository.findByEmail(email);
        if (document != null || user == null) {
            return null;
        } else {
            Document documentForPersisting = Document.builder()
                    .serial(serial)
                    .number(number)
                    .user(user)
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
                    .email(document.getUser().getEmail())
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
        User user = userRepository.findByEmail(document.getUser().getEmail());
        if (user != null) {
            return DocumentDTO.builder()
                    .number(document.getNumber())
                    .serial(document.getSerial())
                    .email(user.getEmail())
                    .build();
        } else {
            return null;
        }
    }

    private Document parse(DocumentDTO documentDTO) {
        User user = userRepository.findByEmail(documentDTO.getEmail());
        if (user != null) {
            return Document.builder()
                    .number(documentDTO.getNumber())
                    .serial(documentDTO.getSerial())
                    .user(user)
                    .build();
        } else {
            return null;
        }
    }
}
