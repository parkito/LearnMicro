package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.dto.ParcelDTO;
import com.parkito.learnmicro.monolith.entity.Document;
import com.parkito.learnmicro.monolith.entity.Parcel;
import com.parkito.learnmicro.monolith.entity.User;
import com.parkito.learnmicro.monolith.repository.DocumentRepository;
import com.parkito.learnmicro.monolith.repository.ParcelRepository;
import com.parkito.learnmicro.monolith.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Service
public class ParcelService {
    // TODO: 11/7/2017 spring data empty list when nothing found
    // TODO: 11/7/2017 spring data null when nothing entity found

    private final UserRepository userRepository;
    private final ParcelRepository parcelRepository;
    private final DocumentRepository documentRepository;

    @Autowired
    public ParcelService(UserRepository userRepository, ParcelRepository parcelRepository, DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.parcelRepository = parcelRepository;
        this.documentRepository = documentRepository;
    }

    public ParcelDTO findParcelByNumber(long number) {
        Parcel parcel = parcelRepository.findByNumber(number);
        if (parcel != null) {
            return convert(parcel);
        } else {
            return null;
        }
    }

    public List<ParcelDTO> getAllParcelsForUser(String email) {
        return parcelRepository.findAllParcelsForUser(email).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public ParcelDTO createParcel(long number, double weight, double price, String emailFrom, String emailTo, String status) {
        User userFrom = userRepository.findByEmail(emailFrom);
        User userTo = userRepository.findByEmail(emailTo);
        Parcel parcel = parcelRepository.findByNumber(number);

        if (userFrom != null && userTo != null && parcel != null) {
            Parcel parcelForPersisting = Parcel.builder()
                    .number(number)
                    .weight(weight)
                    .price(price)
                    .status(Parcel.Status.fromCode(status))
                    .from(userFrom)
                    .to(userTo)
                    .build();
            return convert(parcelRepository.save(parcelForPersisting));
        } else {
            return null;
        }
    }

    public ParcelDTO getParcelForUser(long parcelNumber, String userEmail, String docSerial, String docNumber) {
        User user = userRepository.findByEmail(userEmail);
        Document userDocument = documentRepository.findBySerialAndNumber(docSerial, docNumber);
        Parcel parcel = parcelRepository.findByNumber(parcelNumber);

        if (user != null && userDocument != null && parcel != null) {
            if (parcel.getStatus() != Parcel.Status.DELIVERED) {
                parcel.setStatus(Parcel.Status.DELIVERED);
                Parcel savedParcel = parcelRepository.save(parcel);
                return convert(savedParcel);
            }
        }
        return null;
    }

    private Parcel parse(ParcelDTO parcelDTO) {
        return Parcel.builder()
                .number(parcelDTO.getNumber())
                .price(parcelDTO.getPrice())
                .weight(parcelDTO.getWeight())
                .status(Parcel.Status.fromCode(parcelDTO.getStatus()))
                .from(userRepository.findByEmail(parcelDTO.getUserFrom()))
                .to(userRepository.findByEmail(parcelDTO.getUserTo()))
                .build();
    }

    private ParcelDTO convert(Parcel parcel) {
        return ParcelDTO.builder()
                .number(parcel.getNumber())
                .price(parcel.getPrice())
                .weight(parcel.getWeight())
                .status(parcel.getStatus().getCode())
                .userFrom(parcel.getFrom().getEmail())
                .userTo(parcel.getTo().getEmail())
                .build();
    }


}
