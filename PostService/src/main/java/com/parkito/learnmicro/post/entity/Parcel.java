package com.parkito.learnmicro.post.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Entity
@Builder
@Data
public class Parcel implements Serializable {
    @Id
    @GeneratedValue
    private long parcelId;

    @Column(nullable = false, unique = true)
    private long number;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String from;

    @Column(nullable = false)
    private String to;

}