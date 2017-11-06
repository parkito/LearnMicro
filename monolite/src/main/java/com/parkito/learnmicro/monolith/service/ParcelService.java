package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.controller.BackendClient;
import com.parkito.learnmicro.monolith.dto.ParcelDTO;
import com.parkito.learnmicro.monolith.entity.Parcel;
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
    private final BackendClient backendClient;
    private final UserRepository userRepository;

    @Autowired
    public ParcelService(BackendClient backendClient, UserRepository userRepository) {
        this.backendClient = backendClient;
        this.userRepository = userRepository;
    }

    public Parcel convert(ParcelDTO parcelDTO) {
        return Parcel.builder()
                .number(parcelDTO.getNumber())
                .price(parcelDTO.getPrice())
                .weight(parcelDTO.getWeight())
                .status(Parcel.Status.fromCode(parcelDTO.getStatus()))
                .from(userRepository.findByEmail(parcelDTO.getUserFrom()))
                .to(userRepository.findByEmail(parcelDTO.getUserTo()))
                .build();
    }

    public Parcel findParcelByNumber(long number) {
        return convert(backendClient.getParcelByNumber(number));
    }

    public List<Parcel> getAllParcelsForUser(String email) {
        return backendClient.getAllUserParcels(email)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
