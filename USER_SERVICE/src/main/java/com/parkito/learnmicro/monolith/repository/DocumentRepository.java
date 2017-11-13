package com.parkito.learnmicro.monolith.repository;

import com.parkito.learnmicro.monolith.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findBySerialAndNumber(String serial, String number);

    List<Document> findByUserEmail(String email);
}
