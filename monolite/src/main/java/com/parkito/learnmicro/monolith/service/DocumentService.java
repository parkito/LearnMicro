package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.dto.DocumentDTO;
import com.parkito.learnmicro.monolith.entity.Document;
import com.parkito.learnmicro.monolith.entity.User;
import com.parkito.learnmicro.monolith.repository.DocumentRepository;
import com.parkito.learnmicro.monolith.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
public class DocumentService {
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(UserRepository userRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }

    public DocumentDTO convert(Document document) {
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

    public DocumentDTO createDocument(String serial, String number, String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return convert(
                    Document.builder()
                            .serial(serial)
                            .number(number)
                            .user(user)
                            .build()
            );
        } else {
            return null;
        }
    }

    public boolean deleteDocument(String serial, String number) {
        Document document = documentRepository.findBySerialAndNumber(serial, number);
        if (document != null) {
            documentRepository.delete(document);
        }
        return true;
    }
}
