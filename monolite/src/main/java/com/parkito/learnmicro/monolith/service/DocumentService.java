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

    public Document convert(DocumentDTO documentDTO) {
        User user = userService.findUserByEmail(documentDTO.getEmail());
        if (user == null)
            return null;
        return Document.builder()
                .number(documentDTO.getNumber())
                .serial(documentDTO.getSerial())
                .user(user)
                .build();
    }
}
