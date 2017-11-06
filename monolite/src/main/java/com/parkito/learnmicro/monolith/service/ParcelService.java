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
        return convert(parcelRepository.findByNumber(number));
    }

    public List<ParcelDTO> getAllParcelsForUser(String email) {
        return parcelRepository.findAllParcelsForUser(email)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public ParcelDTO getParcelForUser(long parcelNumber, String email, String docSerial, String docNumber) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        Document document = documentRepository.findBySerialAndNumber(docSerial, docNumber);
        if (document != null && document.getUser().equals(user)) {
            Parcel parcel = parcelRepository.findByNumber(parcelNumber);
            if (parcel != null && parcel.getStatus() != Parcel.Status.DELIVERED) {
                parcel.setStatus(Parcel.Status.DELIVERED);
                return convert(parcel);
            } else {
                return null;
            }
        } else {
            return null;
        }
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
