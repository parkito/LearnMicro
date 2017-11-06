package com.parkito.learnmicro.monolith.repository;

import com.parkito.learnmicro.monolith.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long>{

    Parcel findByNumber(long number);
}
