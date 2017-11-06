package com.parkito.learnmicro.post.repository;

import com.parkito.learnmicro.post.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    Parcel findByNumber(long number);

    List<Parcel> findDistinctByFrom(String email);
}
