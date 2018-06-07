package com.parkito.learnmicro.monolith.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Builder
@Data
public class ParcelDTO implements Serializable {
    private long number;
    private String status;
    private double weight;
    private double price;
    private String userFrom;
    private String userTo;
}