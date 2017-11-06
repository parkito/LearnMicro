package com.parkito.learnmicro.monolith.service;

import com.parkito.learnmicro.monolith.entity.Parcel;
import com.parkito.learnmicro.monolith.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
public class ParcelService {
    private final ParcelRepository parcelRepository;

    @Autowired
    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    public Parcel findParcelByNumber(long number) {
        return parcelRepository.findByNumber(number);
    }

}
