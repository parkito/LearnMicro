package com.parkito.learnmicro.post.service;

import com.parkito.learnmicro.post.dto.ParcelDTO;
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
    private final ParcelRepository parcelRepository;

    @Autowired
    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    public ParcelDTO convert(Parcel parcel) {
        return ParcelDTO.builder()
                .number(parcel.getNumber())
                .price(parcel.getPrice())
                .status(parcel.getStatus())
                .userFrom(parcel.getFrom())
                .userTo(parcel.getTo())
                .build();
    }


    public Parcel findParcelByNumber(long number) {
        return parcelRepository.findByNumber(number);
    }

    public List<ParcelDTO> getAllParcelsForUser(String email) {
        return parcelRepository.findDistinctByFrom(email).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
