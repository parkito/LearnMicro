package com.parkito.learnmicro.post.service;

import com.parkito.learnmicro.post.controller.PostRestClient;
import com.parkito.learnmicro.post.dto.DocumentDTO;
import com.parkito.learnmicro.post.dto.ParcelDTO;
import com.parkito.learnmicro.post.dto.UserDTO;
import com.parkito.learnmicro.post.entity.Parcel;
import com.parkito.learnmicro.post.repository.ParcelRepository;
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
    private final PostRestClient postRestClient;
    private final ParcelRepository parcelRepository;

    @Autowired
    public ParcelService(ParcelRepository parcelRepository, PostRestClient postRestClient) {
        this.postRestClient = postRestClient;
        this.parcelRepository = parcelRepository;
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
        return parcelRepository.findAllParcelsForUser(email)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public ParcelDTO createParcel(long number, double weight, double price, String emailFrom, String emailTo, String status) {
        UserDTO userFrom = postRestClient.findUserByEmail(emailFrom);
        UserDTO userTo = postRestClient.findUserByEmail(emailTo);
        Parcel parcel = parcelRepository.findByNumber(number);

        if (userFrom != null && userTo != null && parcel == null) {
            Parcel parcelForPersisting = Parcel.builder()
                    .number(number)
                    .weight(weight)
                    .price(price)
                    .status(Parcel.Status.fromCode(status))
                    .userFrom(userFrom.getEmail())
                    .userTo(userTo.getEmail())
                    .build();
            Parcel persistedParcel = parcelRepository.save(parcelForPersisting);
            return convert(persistedParcel);
        } else {
            return null;
        }
    }

    public ParcelDTO getParcelForUser(long parcelNumber, String userEmail, String docSerial, String docNumber) {
        UserDTO user = postRestClient.findUserByEmail(userEmail);
        DocumentDTO userDocument = postRestClient.findDocumentBySerialAndNumber(docSerial, docNumber);
        Parcel parcel = parcelRepository.findByNumber(parcelNumber);

        if (user != null && userDocument != null && parcel != null && parcel.getUserTo().equals(user.getEmail())) {
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
                .userFrom(postRestClient.findUserByEmail(parcelDTO.getUserFrom()).getEmail())
                .userTo(postRestClient.findUserByEmail(parcelDTO.getUserTo()).getEmail())
                .build();
    }

    private ParcelDTO convert(Parcel parcel) {
        return ParcelDTO.builder()
                .number(parcel.getNumber())
                .price(parcel.getPrice())
                .weight(parcel.getWeight())
                .status(parcel.getStatus().getCode())
                .userFrom(parcel.getUserFrom())
                .userTo(parcel.getUserTo())
                .build();
    }
}
