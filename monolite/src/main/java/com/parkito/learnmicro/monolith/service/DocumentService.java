package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.dto.DocumentDTO;
import com.parkito.learnmicro.monolith.entity.Document;
import com.parkito.learnmicro.monolith.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
public class DocumentService {
    private UserService userService;

    @Autowired
    public DocumentService(UserService userService) {
        this.userService = userService;
    }

    public DocumentDTO convert(Document document) {
        User user = userService.findUserByEmail(document.getUser().getEmail());
        if (user == null)
            return null;
        return DocumentDTO.builder()
                .number(document.getNumber())
                .serial(document.getSerial())
                .email(user.getEmail())
                .build();
    }

    public DocumentDTO createDocument(String serial, String number, String email) {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return null;
        } else {
            return convert(
                    Document.builder()
                            .serial(serial)
                            .number(number)
                            .user(user)
                            .build()
            );
        }
    }
}
