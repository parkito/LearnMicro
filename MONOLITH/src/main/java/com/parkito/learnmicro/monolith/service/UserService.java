package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.dto.UserDTO;
import com.parkito.learnmicro.monolith.entity.Document;
import com.parkito.learnmicro.monolith.entity.User;
import com.parkito.learnmicro.monolith.repository.DocumentRepository;
import com.parkito.learnmicro.monolith.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
public class UserService {
    private static final String COMMA_SEPARATOR = ",";

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public UserService(UserRepository userRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
    }

    public boolean createUser(String email, String firstName, String secondName) {
        User existedUser = userRepository.findByEmail(email);
        if (existedUser == null) {
            User user = User.builder()
                    .email(email)
                    .firstName(firstName)
                    .lastName(secondName)
                    .build();
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        } else {
            return convert(user);
        }
    }

    public boolean deleteUserByEmail(String email) {
        User userForDelete = userRepository.findByEmail(email);
        if (userForDelete != null) {
            userRepository.delete(userForDelete);
            return true;
        } else {
            return false;
        }
    }

    private UserDTO convert(User user) {
        if (user == null) {
            return null;
        } else {
            return UserDTO.builder()
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .firstName(user.getLastName())
                    .serials(user.getDocuments().stream()
                            .map(d -> d.getSerial() + COMMA_SEPARATOR + d.getNumber())
                            .collect(Collectors.toList())
                    )
                    .build();
        }
    }

    private User parse(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user == null) {
            return null;
        } else {
            return User.builder()
                    .email(userDTO.getEmail())
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .documents(userDTO.getSerials().stream()
                            .map(s -> {
                                        List<String> serialAndNumber = Collections.list(new StringTokenizer(s, ",")).stream()
                                                .map(token -> (String) token).collect(Collectors.toList());
                                        Document document = documentRepository.findBySerialAndNumber(serialAndNumber.get(0), serialAndNumber.get(1));
                                        if (document != null) {
                                            return document;
                                        } else {
                                            return null;
                                        }
                                    }
                            ).filter(Objects::nonNull)
                            .collect(Collectors.toList())
                    )
                    .build();
        }
    }
}
