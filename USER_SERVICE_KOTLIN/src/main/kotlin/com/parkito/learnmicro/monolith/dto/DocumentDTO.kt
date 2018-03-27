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
public class DocumentDTO implements Serializable {
    private String serial;
    private String number;
    private String email;
}
